/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.util.builder.TextoBuilder;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ItemOrcado;
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
import javax.faces.context.FacesContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Rafael
 */
public final class ImpressoraDeLayoutTexto {

    //Diretório para Console: System.getProperty("user.dir") + "\\src\\main\\resources\\layoutsTexto\\";
    private String diretorio = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "\\WEB-INF\\classes\\layoutsTexto\\";
    private List<TextoBuilder> impressoras = new ArrayList<>();
    private TextoBuilder builder = new TextoBuilder();
    private JSONObject jsonObject;
    private Integer numeroDePaginas = 1;
    private Integer altura;
    private int largura;
    private List<GenericLayout> genericDadosList;
    private List<GenericLayout> tipoCobrancaCursor;
    private List<GenericLayout> itemOrcadoCursor;
    private List<PropertiesObject> tipoCobrancaProperties;
    private List<PropertiesObject> formaCobrancaProperties;
    private List<GenericLayout> formaCobrancaCursor;
    private List<PropertiesObject> itemOrcadoProperties;
    private List<PropertiesObject> itemDeCondicionalProperties;
    private List<GenericLayout> itemDeCondicionalCursor;
    private List<PropertiesObject> itemDeNotaProperties;
    private List<GenericLayout> itemDeNotaCursor;
    private JSONArray layout;

    public ImpressoraDeLayoutTexto(String layoutJson, Class classeDeDados, Object objeto) {
        this.diretorio += layoutJson + ".json";
        buscaJson();
        setupPage();
        if (objeto instanceof List) {
            for (Object obj : (List) objeto) {
                geradorDeDadosDaPagina(classeDeDados, obj);
            }
        } else {
            geradorDeDadosDaPagina(classeDeDados, objeto);
        }
    }

    private void setupPage() {

        // Area "report" do layout
        JSONArray reportArray = (JSONArray) jsonObject.get("report");
        JSONObject report = (JSONObject) reportArray.get(0);
        largura = ((Long) report.get("width")).intValue();
        altura = ((Long) report.get("height")).intValue();

        // Area "layout" do layout
        layout = (JSONArray) jsonObject.get("layout");

        // Area "dados" do layout
        JSONArray dados = (JSONArray) jsonObject.get("dados");
        genericDadosList = criaListaGenericLayout(dados);

        // Area "tipoCobranca" do layout
        JSONArray tipoCobrancaArrayProperties = (JSONArray) jsonObject.get("tipoCobranca-properties");
        JSONArray tipoCobrancaArrayCursor = (JSONArray) jsonObject.get("tipoCobranca-cursor");
        if (tipoCobrancaArrayProperties != null && tipoCobrancaArrayCursor != null) {
            tipoCobrancaProperties = criaListaProperties(tipoCobrancaArrayProperties);
            tipoCobrancaCursor = criaListaGenericLayout(tipoCobrancaArrayCursor);
        }

        // Area "formaCobranca" do layout
        JSONArray formaCobrancaArrayProperties = (JSONArray) jsonObject.get("formaCobranca-properties");
        JSONArray formaCobrancaArrayCursor = (JSONArray) jsonObject.get("formaCobranca-cursor");
        if (formaCobrancaArrayProperties != null && formaCobrancaArrayCursor != null) {
            formaCobrancaProperties = criaListaProperties(formaCobrancaArrayProperties);
            formaCobrancaCursor = criaListaGenericLayout(formaCobrancaArrayCursor);
        }

        // Area "itemOrcado" do layout
        JSONArray itemOrcadoArrayProperties = (JSONArray) jsonObject.get("itemOrcado-properties");
        JSONArray itemOrcadoArrayCursor = (JSONArray) jsonObject.get("itemOrcado-cursor");
        if (itemOrcadoArrayProperties != null && itemOrcadoArrayCursor != null) {
            itemOrcadoProperties = criaListaProperties(itemOrcadoArrayProperties);
            itemOrcadoCursor = criaListaGenericLayout(itemOrcadoArrayCursor);
        }

        // Area "itemDeNota" do layout
        JSONArray itemDeNotaArrayProperties = (JSONArray) jsonObject.get("itemDeNota-properties");
        JSONArray itemDeNotaArrayCursor = (JSONArray) jsonObject.get("itemDeNota-cursor");
        if (itemDeNotaArrayProperties != null && itemDeNotaArrayCursor != null) {
            itemDeNotaProperties = criaListaProperties(itemDeNotaArrayProperties);
            itemDeNotaCursor = criaListaGenericLayout(itemDeNotaArrayCursor);
        }

        // Area "itemDeCondicional" do layout
        JSONArray itemDeCondicionalArrayProperties = (JSONArray) jsonObject.get("itemDeCondicional-properties");
        JSONArray itemDeCondicionalArrayCursor = (JSONArray) jsonObject.get("itemDeCondicional-cursor");
        if (itemDeCondicionalArrayProperties != null && itemDeCondicionalArrayCursor != null) {
            itemDeCondicionalProperties = criaListaProperties(itemDeCondicionalArrayProperties);
            itemDeCondicionalCursor = criaListaGenericLayout(itemDeCondicionalArrayCursor);
        }
    }

    private void geradorDeDadosDaPagina(Class classeDeDados, Object objeto) {
        double numPagTipo = 1;
        double numPagForma = 1;
        double numItemOrcado = 1;
        double numItemDeNota = 1;
        double numItemDeCondicional = 1;
        while (true) {
            instalaPropriedadesDaPagina();
            constroiLayout();
            constroiDados(classeDeDados, objeto);

            //Constroi dados de cursores
            if (tipoCobrancaProperties != null && tipoCobrancaCursor != null && numPagTipo >= numeroDePaginas) {
                numPagTipo = new ConstructorCursor<TipoDeCobranca>().construct(TipoDeCobranca.class, classeDeDados, objeto, tipoCobrancaProperties, tipoCobrancaCursor);
            }
            if (formaCobrancaProperties != null && formaCobrancaCursor != null && numPagForma >= numeroDePaginas) {
                numPagForma = new ConstructorCursor<TipoDeCobranca>().construct(TemplateFormaPagamento.class, classeDeDados, objeto, formaCobrancaProperties, formaCobrancaCursor);
            }
            if (itemOrcadoProperties != null && itemOrcadoCursor != null && numItemOrcado >= numeroDePaginas) {
                numItemOrcado = new ConstructorCursor<ItemOrcado>().construct(ItemOrcado.class, classeDeDados, objeto, itemOrcadoProperties, itemOrcadoCursor);
            }
            if (itemDeNotaProperties != null && itemDeNotaCursor != null && numItemDeNota >= numeroDePaginas) {
                numItemDeNota = new ConstructorCursor<ItemDeNota>().construct(ItemDeNota.class, classeDeDados, objeto, itemDeNotaProperties, itemDeNotaCursor);
            }
            if (itemDeCondicionalProperties != null && itemDeCondicionalCursor != null && numItemDeCondicional >= numeroDePaginas) {
                numItemDeCondicional = new ConstructorCursor<ItemDeCondicional>().construct(ItemDeCondicional.class, classeDeDados, objeto, itemDeCondicionalProperties, itemDeCondicionalCursor);
            }
            //Adiciona a pagina da impressora
            impressoras.add(builder);
            builder = new TextoBuilder();

            if (numPagTipo <= numeroDePaginas && numPagForma <= numeroDePaginas && numItemOrcado <= numeroDePaginas && numItemDeNota <= numeroDePaginas) {
                break;
            }
            numeroDePaginas++;
        }

        geraNumeroDePagina();
    }

    private void geraNumeroDePagina() {
        GenericLayout gen = genericDadosList.stream().filter(g -> g.getColumn().equals("pageNumber")).findFirst().orElse(null);
        if (gen != null) {
            int i = 1;
            for (TextoBuilder imp : impressoras) {
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
        //lin x col
        builder.setTamanhoDaPagina(altura, largura);
    }

    private void constroiLayout() {
        builder.carregaLayout(layout);
    }

    /**
     *
     * @param classeDeDados
     * @param objeto
     */
    private void constroiDados(Class classeDeDados, Object objeto) {
        try {

            for (GenericLayout g : genericDadosList) {
                escreveNoRelatorio(classeDeDados, g, objeto);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException nsm) {
            throw new RuntimeException("Metodo nao encontrado " + nsm.getMessage());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
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
                    builder.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                } else {
                    Method field = Class.forName(genericLayout.getClazz()).getMethod("get" + genericLayout.getColumn().substring(0, 1).toUpperCase()
                            + genericLayout.getColumn().substring(1), null);
                    obj = field.invoke(objeto);
                    String toString = "";
                    try {
                        toString = obj.toString();
                    } catch (NullPointerException n) {
                    }
                    builder.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
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
                            builder.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
                        } else {
                            method = clazzIterada.getMethod("get" + genericLayout.getColumn().substring(0, 1).toUpperCase() + genericLayout.getColumn().substring(1), null);
                            String toString = "";
                            try {
                                toString = method.invoke(obj).toString();
                            } catch (NullPointerException n) {
                            }
                            builder.insereTextoNaLinhaColuna(genericLayout.getTop(), genericLayout.getLeft(), align(genericLayout, toString));
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

    private String align(GenericLayout genericLayout, String str) {
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

    public void exibirNoConsole() {
        for (TextoBuilder imp : impressoras) {
            imp.exibeNoConsole();
        }
    }

    public void imprimir(String caminhoImpressora) throws DadoInvalidoException {
        caminhoImpressora = caminhoImpressora.trim();
        if (caminhoImpressora != null) {
            WarningMessage.print(new BundleUtil().getLabel("Imprimindo"));
            for (TextoBuilder imp : impressoras) {
                new MatrixPrinter(caminhoImpressora).imprimir(imp.getArrayEmLinha());
            }
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Caminho_Impressora_Texto_Nao_Configurada_Nas_Configuracoes"));
        }
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

    private List<GenericLayout> criaListaGenericLayout(JSONArray dados) {
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

        public double construct(Class clazz, Class classeDeDados, Object objeto, List<PropertiesObject> properties, List<GenericLayout> cursor) {
            try {

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
                        GenericLayout g = new GenericLayout(generic.getClazz(), generic.getColumn(), generic.getLeft(), generic.getTop(), generic.getAlign(), generic.getSize());
                        g.setLeft(g.getLeft() + leftExtra);
                        g.setTop(start + inicio);
                        escreveNoRelatorio(clazz, g, list.get(index));
                    }

                    inicio = inicio + height;
                    if ((properties.get(indexProperties).getCount()) == inicio) {
                        indexProperties++;
                        inicio = 0;
                    }
                }

                return Math.ceil((double) list.size() / elementosPorPagina);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException nsm) {
                throw new RuntimeException("Metodo nao encontrado " + nsm.getMessage());
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ImpressoraDeLayoutTexto.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }

        private String buscaNomeDeMetodo(Class clazz, Class classeDeDados, Object objeto) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {
            Method[] methods = classeDeDados.getMethods();
            for (Method m : methods) {
                if (m.getReturnType().equals(List.class)) {
                    List l = (List) m.invoke(objeto, null);
                    if (l == null || l.isEmpty()) {
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
