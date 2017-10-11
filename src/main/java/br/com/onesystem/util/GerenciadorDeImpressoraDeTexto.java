/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.CaminhoDeClasse;
import br.com.onesystem.reportTemplate.TemplateFormaPagamento;
import br.com.onesystem.util.StringAlignUtils.Alignment;
import br.com.onesystem.valueobjects.FormatPage;
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
        double numItemOrcado = 1;
        double numItemDeNota = 1;
        while (true) {
            instalaPropriedadesDaPagina();
            constroiLayout();
            constroiDados(classeDeDados, objeto);

            if (numPagTipo >= numeroDePaginas) {
                numPagTipo = new ConstructorCursor<TipoDeCobranca>().construct(TipoDeCobranca.class, classeDeDados, objeto, "tipoCobranca");
            }
            if (numPagForma >= numeroDePaginas) {
                numPagForma = new ConstructorCursor<TipoDeCobranca>().construct(TemplateFormaPagamento.class, classeDeDados, objeto, "formaCobranca");
            }
            if (numItemOrcado >= numeroDePaginas) {
                numItemOrcado = new ConstructorCursor<ItemOrcado>().construct(ItemOrcado.class, classeDeDados, objeto, "itemOrcado");
            }
            if (numItemDeNota >= numeroDePaginas) {
                numItemDeNota = new ConstructorCursor<ItemDeNota>().construct(ItemDeNota.class, classeDeDados, objeto, "itemDeNota");
            }
            impressoras.add(impressora);
            impressora = new ImpressoraDeTexto();

            if (numPagTipo <= numeroDePaginas && numPagForma <= numeroDePaginas && numItemOrcado <= numeroDePaginas && numItemDeNota <= numeroDePaginas) {
                break;
            }
            numeroDePaginas++;
        }

        geraNumeroDePagina();
    }

    private void geraNumeroDePagina() {
        JSONArray dados = (JSONArray) jsonObject.get("dados");
        List<GenericLayout> list = criaListaGenericLayout(dados);
        GenericLayout gen = list.stream().filter(g -> g.getColumn().equals("pageNumber")).findFirst().orElse(null);
        if (gen != null) {
            int i = 1;
            for (ImpressoraDeTexto imp : impressoras) {
                String str = i + " de " + numeroDePaginas;
                imp.insereTextoNaLinhaColuna(gen.getTop(), gen.getLeft(), align(gen, str));
                i++;
            }
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
        try {
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
                        try {
                            str += ", " + method.invoke(objeto).toString();
                        } catch (NullPointerException n) {
                            str += ", ";
                        }
                    }
                    String toString = str.substring(2);
                    impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                } else {
                    Method field = Class.forName(genericLayout.getClazz()).getMethod("get" + genericLayout.getColumn().substring(0, 1).toUpperCase()
                            + genericLayout.getColumn().substring(1), null);
                    obj = field.invoke(objeto);
                    String toString = "";
                    try {
                        toString = obj.toString();
                    } catch (NullPointerException n) {
                    }
                    impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                }
            } else if (!clazzDado.equals(Object.class)) {
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
                                try {
                                    str += ", " + method.invoke(obj).toString();
                                } catch (NullPointerException n) {
                                    str += ", ";
                                }
                            }
                            String toString = str.substring(2);
                            impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                        } else {
                            method = clazzIterada.getMethod("get" + genericLayout.getColumn().substring(0, 1).toUpperCase() + genericLayout.getColumn().substring(1), null);
                            String toString = "";
                            try {
                                toString = method.invoke(obj).toString();
                            } catch (NullPointerException n) {
                            }
                            impressora.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                        }
                    } else {
                        throw new RuntimeException("Classe " + clazzIterada + " nao encontrada!");
                    }
                }

            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro na coluna: " + genericLayout.getColumn() + " da classe: " + genericLayout.getClazz() + "\n"
                    + ex.getMessage());

        }
    }

    public String align(GenericLayout genericLayout, String str) {
        if (genericLayout.getAlign() != null && genericLayout.getSize() != null) {
            if (str.length() > genericLayout.getSize()) {
                str = str.substring(0, genericLayout.getSize());
            }
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

    public void imprimir(String impressora) {
        for (ImpressoraDeTexto imp : impressoras) {
            new MatrixPrinter(impressora).imprimir(imp.getArrayEmLinha());
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

    public class ConstructorCursor<T> {

        public double construct(Class clazz, Class classeDeDados, Object objeto, String name) {
            try {
                JSONArray jsonArray = (JSONArray) jsonObject.get(name + "-properties");
                JSONArray jsonCursor = (JSONArray) jsonObject.get(name + "-cursor");
                if (jsonArray == null || jsonCursor == null) {
                    throw new EDadoInvalidoException("");
                }

                List<PropertiesObject> properties = criaListaProperties(jsonArray);
                List<GenericLayout> cursor = criaListaGenericLayout(jsonCursor);

                //Busca nome do método que retorna a lista de tipo de cobranca
                String nomeMetodo = buscaNomeDeMetodo(clazz, classeDeDados, objeto);
                Method m = classeDeDados.getMethod(nomeMetodo, null);
                List<T> list = (List<T>) m.invoke(objeto, null);

                // Soma o número de counts, para saber o número de elementos possíveis por página
                int elementosPorPagina = properties.stream().mapToInt(PropertiesObject::getCount).sum();
                //Itera sobre cada Tipo de Cobranca
                int inicio = 0;
                // Pega numero de registros possíveis para saber se é possível colocar todos elementos da lista.
                int numRegistros = (numeroDePaginas * elementosPorPagina);
                // Verifica se o numero de elementos possíveis é menor que o tamanho de elementos da lista
                // para saber até onde o indice do loop deve passar
                int indexFim = numRegistros < list.size() ? numRegistros : list.size();
                // Pega o índice de inicio conforme os registros já informados.
                int indexInicio = numRegistros - elementosPorPagina;
                // Inicia o index das propriedades
                int indexProperties = 0;

                for (int index = indexInicio; index < indexFim; index++) {
                    int leftExtra = properties.get(indexProperties).getLeftExtra() != 0 ? properties.get(indexProperties).getLeftExtra() + 1 : 0;
                    int start = properties.get(indexProperties).getStart();
                    int height = properties.get(indexProperties).getHeight();

                    for (GenericLayout generic : cursor) {
                        generic.setLeft(generic.getLeft() + leftExtra);
                        generic.setTop(start + inicio);
                        escreveNoRelatorio(clazz, generic, list.get(index));
                    }
                    
                    inicio = inicio + height;
                    if ((properties.get(indexProperties).getCount() - 1) == index) {
                        indexProperties++;
                    }
                }

                return Math.ceil((double) list.size() / elementosPorPagina);
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

        private List<PropertiesObject> criaListaProperties(JSONArray jsonArray) {
            List<PropertiesObject> properties = new ArrayList<PropertiesObject>();
            for (Object o : jsonArray) {
                JSONObject report = (JSONObject) o;
                int start = ((Long) report.get("start")).intValue();
                int count = ((Long) report.get("count")).intValue();
                int height = ((Long) report.get("height")).intValue();
                int leftExtra = report.get("leftExtra") == null ? 0 : ((Long) report.get("leftExtra")).intValue();
                properties.add(new PropertiesObject(start, count, height, leftExtra));
            }
            return properties;
        }

        private String buscaNomeDeMetodo(Class clazz, Class classeDeDados, Object objeto) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {
            //Busca nome do método que retorna a lista de tipo de cobranca
            System.out.println("C: " + clazz + " - cl" + classeDeDados);
            Method[] methods = classeDeDados.getMethods();
            for (Method m : methods) {
                if (m.getReturnType().equals(List.class)) {
                    List l = (List) m.invoke(objeto, null);
                    if (l.isEmpty()) {
                        continue;
                    } else {
                        if (l.get(0).getClass().equals(clazz)) {
                            return m.getName();
                        }
                    }
                }
            }
            return null;
        }

        public class PropertiesObject {

            private int start;
            private int count;
            private int height;
            private int leftExtra;

            public PropertiesObject(int start, int count, int height, int leftExtra) {
                this.start = start;
                this.count = count;
                this.height = height;
                this.leftExtra = leftExtra;
            }

            public int getStart() {
                return start;
            }

            public int getCount() {
                return count;
            }

            public int getHeight() {
                return height;
            }

            public int getLeftExtra() {
                return leftExtra;
            }

        }

    }

}
