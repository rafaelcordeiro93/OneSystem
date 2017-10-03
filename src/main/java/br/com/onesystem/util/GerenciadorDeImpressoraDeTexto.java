/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.CaminhoDeClasse;
import br.com.onesystem.reportTemplate.TemplateFormaPagamento;
import br.com.onesystem.util.StringAlignUtils.Alignment;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Rafael
 */
public final class GerenciadorDeImpressoraDeTexto {

    private String diretorio = System.getProperty("user.dir") + "\\src\\main\\resources\\layoutsTexto\\";
    private List<ImpressoraDeTexto> impressoras = new ArrayList<>();
    private ImpressoraDeTexto impressora = new ImpressoraDeTexto();
    private JSONObject jsonObject;
    private Integer numeroDePaginas = 1;

    public GerenciadorDeImpressoraDeTexto(String layoutJson, Class classeDeDados, Object objeto) {
        this.diretorio += layoutJson;
        buscaJson();
        geradorDeDadosDaPagina(classeDeDados, objeto);
    }

    private void geradorDeDadosDaPagina(Class classeDeDados, Object objeto) {
        double numPagTipo = 1;
        double numPagForma = 1;
        while (true) {
            instalaPropriedadesDaPagina();
            constroiLayout();
            constroiDados(classeDeDados, objeto);

            if (numPagTipo >= numeroDePaginas) {
                numPagTipo = constroiTiposDeCobranca(classeDeDados, objeto);
            }
            if (numPagForma >= numeroDePaginas) {
                numPagForma = constroiFormaDePagamento(classeDeDados, objeto);
            }
            if (numPagTipo <= numeroDePaginas && numPagForma <= numeroDePaginas) {
                break;
            }
            impressoras.add(impressora);
            impressora = new ImpressoraDeTexto();
            numeroDePaginas++;
        }
    }

    private void buscaJson() {
        try {
            //  JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) new JSONParser().parse(new FileReader(diretorio));
            //JSONObject jsonObject = (JSONObject) obj;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Layout não encontrado no diretório " + diretorio);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (ParseException ex) {
            throw new RuntimeException("Erro de conversão:" + ex.getMessage());
        }
    }

    private void instalaPropriedadesDaPagina() {
        JSONArray reportArray = (JSONArray) jsonObject.get("report");
        JSONObject report = (JSONObject) reportArray.get(0);
        int largura = ((Long) report.get("width")).intValue();
        int altura = ((Long) report.get("height")).intValue();
        //lin x col
        impressora.setTamanhoDaPagina(altura, largura);
    }

    private void constroiLayout() {
        JSONArray layout = (JSONArray) jsonObject.get("layout");
        impressora.carregaLayout(layout);
    }

    /**
     *
     * @param classeDeDados
     * @param objeto
     */
    private void constroiDados(Class classeDeDados, Object objeto) {
        try {

            JSONArray dados = (JSONArray) jsonObject.get("dados");

            for (GenericLayout g : criaListaGenericLayout(dados)) {
                escreveNoRelatorio(classeDeDados, g, objeto);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException nsm) {
            throw new RuntimeException("Metodo nao encontrado " + nsm.getMessage());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void escreveNoRelatorio(Class classeDeDados, GenericLayout genericLayout, Object objeto) throws RuntimeException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        List<CaminhoDeClasse> clazzes = new LeitoraDeCaminhoDeClassesJSON().getCaminhos(classeDeDados);
        CaminhoDeClasse caminhoDeClasse = null;

        Class clazzDado = Class.forName(genericLayout.getClazz());
        Object obj = null;
        if (classeDeDados.equals(clazzDado) || classeDeDados.isAssignableFrom(clazzDado)) {

            if (genericLayout.getColumn().contains("/")) {
                String[] col = genericLayout.getColumn().split("/");
                String str = "";
                for (String s : col) {
                    Method method = Class.forName(genericLayout.getClazz()).getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), null);
                    str += ", " + method.invoke(objeto).toString();
                }
                String toString = str.substring(2);
                impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
            } else {
                Method field = Class.forName(genericLayout.getClazz()).getMethod("get" + genericLayout.getColumn().substring(0, 1).toUpperCase()
                        + genericLayout.getColumn().substring(1), null);
                obj = field.invoke(objeto);
                String toString = obj.toString();
                impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
            }
        } else {
            Class cl = null;
            for (CaminhoDeClasse c : clazzes) {
                if (c.getClasseDeDestino() == clazzDado || c.getClasseDeDestino().isAssignableFrom(clazzDado)) {
                    cl = clazzDado;
                    caminhoDeClasse = c;
                    break;
                }
            }
            if (cl == null) {
                throw new RuntimeException("Configuracao nao definida no arquivo de configuracao Extra Class: " + clazzDado);
            } else {
                String[] split = caminhoDeClasse.getCaminho().split("\\.");

                Method method = null;
                Class clazzIterada = caminhoDeClasse.getClasseDeOrigem();
                obj = objeto;

                for (String s : split) {
                    method = clazzIterada.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), null);
                    obj = method.invoke(obj);
                    clazzIterada = obj.getClass();
                }
                if (clazzIterada == caminhoDeClasse.getClasseDeDestino() || caminhoDeClasse.getClasseDeDestino().isAssignableFrom(clazzIterada)) {
                    if (genericLayout.getColumn().contains("/")) {
                        String[] col = genericLayout.getColumn().split("/");
                        String str = "";
                        for (String s : col) {
                            method = clazzIterada.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), null);
                            str += ", " + method.invoke(obj).toString();
                        }
                        String toString = str.substring(2);
                        impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                    } else {
                        method = clazzIterada.getMethod("get" + genericLayout.getColumn().substring(0, 1).toUpperCase() + genericLayout.getColumn().substring(1), null);

                        String toString = method.invoke(obj).toString();
                        impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                    }
                } else {
                    throw new RuntimeException("Classe " + clazzIterada + " nao encontrada!");
                }
            }
        }
    }

    public String align(GenericLayout genericLayout, String str) {
        if (genericLayout.getAlign() != null && genericLayout.getSize() != null) {
            Alignment alignment = null;
            if ("CENTER".equals(genericLayout.getAlign())) {
                alignment = Alignment.CENTER;
            } else if ("RIGHT".equals(genericLayout.getAlign())) {
                alignment = Alignment.RIGHT;
            } else {
                alignment = Alignment.LEFT;
            }

            StringAlignUtils util = new StringAlignUtils(genericLayout.getSize(), alignment);
            return util.format(str);
        } else {
            return str;
        }
    }

    public double constroiTiposDeCobranca(Class classeDeDados, Object objeto) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("tipoCobranca-properties");
            JSONArray jsonCursor = (JSONArray) jsonObject.get("tipoCobranca-cursor");
            List<GenericLayout> cursor = criaListaGenericLayout(jsonCursor);
            if (jsonArray == null || jsonCursor == null) {
                throw new EDadoInvalidoException("");
            }

            //Busca Propriedades para exibição de tipo de cobrança
            JSONObject report = (JSONObject) jsonArray.get(0);
            int start = ((Long) report.get("start")).intValue();
            int count = ((Long) report.get("count")).intValue();
            int altura = ((Long) report.get("height")).intValue();

            //Busca nome do método que retorna a lista de tipo de cobranca
            String nomeMetodo = buscaNomeDeMetodoDeTipoDeCobranca(classeDeDados, objeto);
            Method m = classeDeDados.getMethod(nomeMetodo, null);
            List<TipoDeCobranca> list = (List<TipoDeCobranca>) m.invoke(objeto, null);

            //Itera sobre cada Tipo de Cobranca
            int i = 0;
            for (TipoDeCobranca tipo : list) {
                for (GenericLayout generic : cursor) {
                    generic.setTop(start + i);
                    escreveNoRelatorio(TipoDeCobranca.class, generic, tipo);
                }
                i = i + altura;
            }

            return Math.ceil((double) list.size() / count);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException nsm) {
            throw new RuntimeException("Metodo nao encontrado " + nsm.getMessage());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DadoInvalidoException die) {
        }
        return 0;
    }

    public double constroiFormaDePagamento(Class classeDeDados, Object objeto) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("formaCobranca-properties");
            JSONArray jsonCursor = (JSONArray) jsonObject.get("formaCobranca-cursor");
            List<GenericLayout> cursor = criaListaGenericLayout(jsonCursor);
            if (jsonArray == null || jsonCursor == null) {
                throw new EDadoInvalidoException("");
            }

            //Busca Propriedades para exibição de tipo de cobrança
            JSONObject report = (JSONObject) jsonArray.get(0);
            int start = ((Long) report.get("start")).intValue();
            int count = ((Long) report.get("count")).intValue();
            int altura = ((Long) report.get("height")).intValue();

            //Busca nome do método que retorna a lista de tipo de cobranca
            String nomeMetodo = buscaNomeDeMetodoDeFormaDeCobranca(classeDeDados, objeto);
            Method m = classeDeDados.getMethod(nomeMetodo, null);
            List<TemplateFormaPagamento> list = (List<TemplateFormaPagamento>) m.invoke(objeto, null);

            //Itera sobre cada Tipo de Cobranca
            int i = 0;
            for (TemplateFormaPagamento tipo : list) {
                for (GenericLayout generic : cursor) {
                    generic.setTop(start + i);
                    escreveNoRelatorio(TemplateFormaPagamento.class, generic, tipo);
                }
                i = i + altura;
            }
            return Math.ceil((double) list.size() / count);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException nsm) {
            throw new RuntimeException("Metodo nao encontrado " + nsm.getMessage());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GerenciadorDeImpressoraDeTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DadoInvalidoException die) {
        }

        return 0;
    }

    private String buscaNomeDeMetodoDeTipoDeCobranca(Class classeDeDados, Object objeto) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {
        //Busca nome do método que retorna a lista de tipo de cobranca
        Method[] methods = classeDeDados.getMethods();
        for (Method m : methods) {
            if (m.getReturnType().equals(List.class)) {
                List l = (List) m.invoke(objeto, null);
                if (l.isEmpty()) {
                    continue;
                } else {
                    if (l.get(0).getClass().equals(TipoDeCobranca.class)) {
                        return m.getName();
                    }
                }
            }
        }
        return null;
    }

    private String buscaNomeDeMetodoDeFormaDeCobranca(Class classeDeDados, Object objeto) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {
        //Busca nome do método que retorna a lista de tipo de cobranca
        Method[] methods = classeDeDados.getMethods();
        for (Method m : methods) {
            if (m.getReturnType().equals(List.class)) {

                List l = (List) m.invoke(objeto, null);
                if (l.isEmpty()) {
                    continue;
                } else {
                    if (l.get(0).getClass().equals(TemplateFormaPagamento.class)) {
                        return m.getName();
                    }
                }
            }
        }
        return null;
    }

    public void imprimir() {
        for (ImpressoraDeTexto imp : impressoras) {
            imp.toPrinterMatricial();
        }
    }
    
    public void paraArquivo() {
        int i = 1;
        for (ImpressoraDeTexto imp : impressoras) {
            imp.toFile("imp " + i);
            i++;
        }
    }

    public void exibirNoConsole() {
        for (ImpressoraDeTexto imp : impressoras) {
            imp.exibeNoConsole();
        }
    }

    public List<GenericLayout> criaListaGenericLayout(JSONArray dados) {
        List<GenericLayout> listaLayout = new ArrayList<>();
        for (Object o : dados) {
            JSONObject j = (JSONObject) o;
            String tabela = (String) j.get("class");
            String coluna = (String) j.get("column");
            Integer left = ((Long) j.get("left")).intValue();
            Integer top = j.get("top") == null ? null : ((Long) j.get("top")).intValue();
            String align = j.get("align") == null ? null : (String) j.get("align");
            Integer size = j.get("size") == null ? null : ((Long) j.get("size")).intValue();

            GenericLayout g = new GenericLayout(tabela, coluna, left, top, align, size);
            listaLayout.add(g);
        }
        return listaLayout;
    }

}
