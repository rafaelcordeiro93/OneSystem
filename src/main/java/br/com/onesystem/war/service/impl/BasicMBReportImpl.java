/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service.impl;

import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.services.impl.FormatacaoNumeroRelatorio;
import br.com.onesystem.util.ImpressoraDeRelatorioDinamico;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.FilterModel;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.valueobjects.TipoDeBusca;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javassist.Modifier;
import javax.inject.Named;
import net.sf.dynamicreports.report.exception.DRException;
import org.primefaces.event.ReorderEvent;
import org.reflections.Reflections;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.Totalizador;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
public abstract class BasicMBReportImpl<T> {

    private Class dao;
    private Class clazz;
    private List<String> consulta = new ArrayList<>();
    private Date consultaDate;
    private List<FilterModel> filtros = new ArrayList<>();
    private Coluna campoSelecionado;
    private String campoSelecionadoString;
    private String colunaParaTotalizadorString;
    private Coluna colunaParaTotalizadorSelecionada;
    private TipoDeBusca tipoDeBuscaSelecionada = TipoDeBusca.CONTENDO;
    private Totalizador totalizador;
    private List<Model> modelDisponivelSelecionado = new ArrayList<>();
    private List<Model> modelExibidoSelecionado = new ArrayList<>();
    protected List<T> registros = new ArrayList<>();
    protected List<T> registrosFiltrados = new ArrayList<>();
    private ModelList<Coluna> campos = new ModelList<Coluna>();
    private ModelList<Coluna> camposDisponiveis = new ModelList<Coluna>();
    private ModelList<Coluna> camposExibidos = new ModelList<Coluna>();
    private List<Class> classes = new ArrayList<>();
    private HashMap<Class, String> mapPath = new HashMap<>();
    protected BundleUtil bundle = new BundleUtil();
    private Coluna siglaMoeda;
    private String nomeDoRelatorio;

    protected abstract void init();

    /**
     * Deve obrigatóriamente chamar o métodos
     * inicializarRegistros,inicializarCampos
     *
     * @param clazz Classe.
     * @param dao Dao utilizado na classe
     */
    protected void initialize(Class clazz, Class<? extends GenericDAO> dao, String nomeDoRelatorio) {
        try {
            this.clazz = clazz;
            this.dao = dao;
            this.nomeDoRelatorio = nomeDoRelatorio;
            inicializarRegistros();
            inicializarCampos();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException cnf) {
            FatalMessage.print("Erro: BasicMBReport: Classe não encontrada!", cnf);
        }
    }

    private void inicializarRegistros() throws InstantiationException, IllegalAccessException {
        buscaDadosDoBancoComFiltros();
    }

    private void inicializarCampos() throws ClassNotFoundException {
        // Adiciona Classe
        List<Class> classesPadroes = new ArrayList<>();
        classes.add(clazz);
        classesPadroes.add(clazz);

        // Adiciona SubClasses
        if (Modifier.isAbstract(clazz.getModifiers())) {
            Reflections r = new Reflections(clazz);
            Object[] array = r.getSubTypesOf(clazz).toArray();
            for (Object o : array) {
                Class c = Class.forName(o.toString().substring(6));
                if (!classes.contains(c)) {
                    classes.add(c);
                }
                classesPadroes.add(c);
            }
        }

        // Adiciona Campos
        String table;
        for (Class c : classes) {
            try {
                table = new BundleUtil().getLabel(c.getSimpleName());
            } catch (MissingResourceException mre) {
                table = "??" + c.getSimpleName() + "??";
            }
            //&& fieldsName.contains(m.getName().substring(3).substring(0, 1).toLowerCase() + m.getName().substring(4)) Summary Row
            List<String> fieldsName = Arrays.asList(c.getDeclaredFields()).stream().map(Field::getName).collect(Collectors.toList());
            for (Method m : c.getMethods()) {
                if (m.getName().contains("get") && !"getClass".equals(m.getName()) && m.getReturnType() != List.class
                        && m.getDeclaringClass().toString().substring(6).equals(c.getName()) && !m.getReturnType().toString().contains("domain")
                        && !m.isAnnotationPresent(MetodoInacessivelRelatorio.class)) {

                    //Busca caminho
                    String[] property = new String[4];
                    if (classesPadroes.contains(c)) {
                        property[0] = m.getName().substring(3);
                        property[0] = property[0].substring(0, 1).toLowerCase() + property[0].substring(1);
                    } else {
                        String propertyComplete = mapPath.get(c);
                        String[] split = propertyComplete.split("\\.");
                        System.arraycopy(split, 0, property, 0, split.length);
                        property[split.length] = m.getName().substring(3);
                        property[split.length] = property[split.length].substring(0, 1).toLowerCase() + property[split.length].substring(1);
                    }

                    //Busca Formatacao
                    TipoFormatacaoNumero formatador = m.isAnnotationPresent(FormatacaoNumeroRelatorio.class) ? m.getAnnotation(FormatacaoNumeroRelatorio.class).formatacaoNumero() : null;

                    //Adiciona nos camposDisponiveis
                    try {
                        String header = m.getName().substring(3);
                        header = new BundleUtil().getLabel(header.substring(0, 1).toUpperCase() + header.substring(1));
                        if (c != clazz) {
                            header = header + " (" + table + ")";
                        }
                        Coluna novoCampo = new Coluna(header, table, property[0], property[1], property[2], property[3], c, m.getReturnType(), formatador);
                        if (!camposExibidos.getList().contains(novoCampo)) {
                            addCampo(novoCampo);
                        }
                    } catch (MissingResourceException mre) {
                        String header = m.getName().substring(3);
                        header = "??" + header.substring(0, 1).toUpperCase() + header.substring(1) + "??";
                        if (c != clazz) {
                            header = header + " (" + table + ")";
                        }
                        Coluna novoCampo = new Coluna(header, table, property[0], property[1], property[2], property[3], c, m.getReturnType(), formatador);
                        if (!camposExibidos.getList().contains(novoCampo)) {
                            addCampo(novoCampo);
                        }
                    }
                }
            }

        }
    }

    public void adicionarParaCampoExibido() {
        if (modelDisponivelSelecionado != null && !modelDisponivelSelecionado.isEmpty()) {
            for (Model m : modelDisponivelSelecionado) {
                camposExibidos.add((Coluna) m.getObject());
                camposDisponiveis.remove(m);
            }
        }
    }

    public void adicionarTodosParaCampoExibido() {
        camposDisponiveis.forEach((m) -> camposExibidos.add((Coluna) m.getObject()));
        camposDisponiveis.removeAll();
    }

    public void excluirDeCampoExibido() {
        if (modelExibidoSelecionado != null && !modelExibidoSelecionado.isEmpty()) {
            for (Model m : modelExibidoSelecionado) {
                camposDisponiveis.add((Coluna) m.getObject());
                camposExibidos.remove(m);
            }
        }
    }

    public void excluirTodosDeCampoExibido() {
        camposExibidos.forEach((m) -> camposDisponiveis.add((Coluna) m.getObject()));
        camposExibidos.removeAll();
    }

    public void onRowReorder(ReorderEvent event) {
        int fromIndex = event.getFromIndex();
        int toIndex = event.getToIndex();

        camposExibidos.reorder(fromIndex, toIndex);
    }

    protected BasicMBReportImpl<T> addExtraClass(Class clazz, String path) {
        this.classes.add(clazz);
        this.mapPath.put(clazz, path);
        if (clazz.equals(Moeda.class)) {
            String[] property = new String[4];
            String[] split = path.split("\\.");
            System.arraycopy(split, 0, property, 0, split.length);
            property[split.length] = "sigla";
            siglaMoeda = new Coluna(null, null, property[0], property[1], property[2], property[3], this.clazz, clazz);
        }
        return this;
    }

    protected BasicMBReportImpl<T> addCampoPadrao(Coluna campo) {
        this.camposExibidos.add(campo);
        addCampo(campo);
        return this;
    }

    private void addCampo(Coluna campo) {
        if (!((campo.getNome() != null && campo.getNome().toLowerCase().contains("format"))
                || (campo.getPropriedade() != null && campo.getPropriedade().toLowerCase().contains("format"))
                || (campo.getPropriedadeDois() != null && campo.getPropriedadeDois().toLowerCase().contains("format"))
                || (campo.getPropriedadeTres() != null && campo.getPropriedadeTres().toLowerCase().contains("format"))
                || (campo.getPropriedadeQuatro() != null && campo.getPropriedadeQuatro().toLowerCase().contains("format")))) {
            this.campos.add(campo);
            this.camposDisponiveis.add(campo);
        }
    }

    public void filterField() {
        for (Model c : campos) {
            if (((Coluna) c.getObject()).getNome().equals(campoSelecionadoString)) {
                campoSelecionado = (Coluna) c.getObject();
            }
        }
    }

    public void filterColumn() {
        for (Model c : campos) {
            if (((Coluna) c.getObject()).getNome().equals(colunaParaTotalizadorString)) {
                colunaParaTotalizadorSelecionada = (Coluna) c.getObject();
            }
        }
    }

    public void delTotalizador(Coluna coluna) {
        // Deleta totalizador na coluna
        camposExibidos.getList().get(camposExibidos.getList().indexOf(coluna)).setTotalizador(null);
    }

    public void delFilter(FilterModel filter) {
        try {
            filtros.remove(filter);
            if (!filtros.isEmpty()) {
                buscaDadosDoBancoComFiltros();
            } else {
                inicializarRegistros();
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            FatalMessage.print("Erro de acesso ao inicializar registros: " + ex.getMessage(), ex);
            Logger.getLogger(BasicMBReportImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void totalizar() {
        //Seta o totalizador selecionado na coluna.
        camposExibidos.getList().get(camposExibidos.getList().indexOf(colunaParaTotalizadorSelecionada)).setTotalizador(totalizador);
    }

    public void pesquisar() {
        try {
            if (campoSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Selecione_Um_Campo_Para_Filtrar"));
            } else if ((consulta == null || consulta.isEmpty()) && consultaDate == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Informe_Filtro_Pesquisa"));
            } else {

                //Cria o conjunto de filtros
                SortedSet filters = new TreeSet();

                //Adiciona os filtros
                FilterModel filtro = new FilterModel(campoSelecionado, filters, tipoDeBuscaSelecionada);

                if (campoSelecionado.getClasseOriginal() != Date.class && !consulta.isEmpty()) {

                    // Faz o tratamento dos filtros em seus devidos Tipos.
                    for (String s : consulta) {
                        //LONG ===============================================================================
                        if (campoSelecionado.getClasseOriginal() == Long.class && !filtros.contains(filtro)) {
                            filters.add(new Long(s));
                        } else if (campoSelecionado.getClasseOriginal() == Long.class && filtros.contains(filtro)) {
                            filtros.get(filtros.indexOf(filtro)).getFilters().add(new Long(s));
                        } //BIGDECIMAL =======================================================================
                        else if (campoSelecionado.getClasseOriginal() == BigDecimal.class && !filtros.contains(filtro)) {
                            s = s.replaceAll(",", ".");
                            filters.add(new BigDecimal(s));
                        } else if (campoSelecionado.getClasseOriginal() == BigDecimal.class && filtros.contains(filtro)) {
                            s = s.replaceAll(",", ".");
                            filtros.get(filtros.indexOf(filtro)).getFilters().add(new BigDecimal(s));
                        } //STRING ============================================================================
                        else if (campoSelecionado.getClasseOriginal() == String.class && !filtros.contains(filtro)) {
                            filters.add(s);
                        } else if (campoSelecionado.getClasseOriginal() == String.class && filtros.contains(filtro)) {
                            filtros.get(filtros.indexOf(filtro)).getFilters().add(s);
                        }
                    }
                    if (!filters.isEmpty()) {
                        filtros.add(new FilterModel(campoSelecionado, filters, tipoDeBuscaSelecionada));
                    }
                } else if (campoSelecionado.getClasseOriginal() == Date.class && !consulta.isEmpty()) {
                    //DATE ====================================================================================
                    if (filtros.contains(filtro)) {
                        filtros.get(filtros.indexOf(filtro)).setFilterDate(getConsultaDate());
                    } else {
                        filtros.add(new FilterModel(campoSelecionado, getConsultaDate(), tipoDeBuscaSelecionada));
                    }
                }

                //Se houver filtros, busca no Banco de Dados.
                if (!filtros.isEmpty()) {
                    buscaDadosDoBancoComFiltros();
                }
            }
        } catch (NumberFormatException nfe) {

        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    private void buscaDadosDoBancoComFiltros() {
        try {
            GenericDAO<T> gDao = (GenericDAO<T>) dao.newInstance();
            alteraConsulta(gDao);

            if (!filtros.isEmpty()) {
                for (FilterModel f : filtros) {
                    if (f.getFilterDate() == null) {
                        gDao.filter(f.getTypeSearch(), f.getField(), f.getFilters());
                    } else {
                        gDao.filter(f.getTypeSearch(), f.getField(), f.getFilterDate());
                    }
                }
            }

            registros = gDao.listaDeResultados();

        } catch (InstantiationException | IllegalAccessException ex) {
            FatalMessage.print("Erro de acesso ao inicializar dao chips: " + ex.getMessage(), ex);
            Logger.getLogger(BasicMBReportImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void alteraConsulta(GenericDAO gDao) {
    }

    public void imprimir() {
        ImpressoraDeRelatorioDinamico impressora = new ImpressoraDeRelatorioDinamico();
        try {
            if (registrosFiltrados == null || registrosFiltrados.isEmpty()) {
                impressora.imprimir(registros, nomeDoRelatorio, camposExibidos.getList(), mapPath.get(Moeda.class)).naWeb();
            } else {
                impressora.imprimir(registrosFiltrados, nomeDoRelatorio, camposExibidos.getList(), mapPath.get(Moeda.class)).naWeb();
            }
        } catch (DRException | IOException | FDadoInvalidoException ex) {
            Logger.getLogger(BasicMBReportImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Implementação para ser feita quando for corrigido o componente
//    Summary Row
//    
//    public void onSummaryRow(Object filter) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ELContext elContext = facesContext.getELContext();
//        ELResolver elResolver = elContext.getELResolver();
//
//        DataTable table = (DataTable) facesContext.getViewRoot().findComponent(":conteudo:cobrancas");
//
//        UIColumn sortColumn = table.getSortColumn();
//        ValueExpression expression = sortColumn.getValueExpression("sortBy");
//        ValueReference reference = ValueExpressionAnalyzer.getReference(elContext, expression);
//        String property = (String) reference.getProperty();
//        System.out.println("Propert: " + property);
//
//        BigDecimal total = BigDecimal.ZERO;
//        List<?> rowList = (List<?>) table.getValue();
//        for (Object row : rowList) {
//            Object value = elResolver.getValue(elContext, row, property);
//            if (filter.equals(value)) {
//                // THIS IS THE ONLY POINT TO CUSTOMIZE
//                total = ((Cobranca) row).getValor().add(total);
//            }
//        }
//
//        List<UIComponent> children = table.getSummaryRow().getChildren();
//        UIComponent column = children.get(children.size() - 1);
//        column.getAttributes().put("total", total);
//    }
    public List<String> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<String> consulta) {
        this.consulta = consulta;
    }

    public Coluna getCampoSelecionado() {
        return campoSelecionado;
    }

    public void setCampoSelecionado(Coluna campoSelecionado) {
        this.campoSelecionado = campoSelecionado;
    }

    public List<Model> getModelDisponivelSelecionado() {
        return modelDisponivelSelecionado;
    }

    public void setModelDisponivelSelecionado(List<Model> modelDisponivelSelecionado) {
        this.modelDisponivelSelecionado = modelDisponivelSelecionado;
    }

    public List<Model> getModelExibidoSelecionado() {
        return modelExibidoSelecionado;
    }

    public void setModelExibidoSelecionado(List<Model> modelExibidoSelecionado) {
        this.modelExibidoSelecionado = modelExibidoSelecionado;
    }

    public ModelList<Coluna> getCampos() {
        return campos;
    }

    public void setCampos(ModelList<Coluna> campos) {
        this.campos = campos;
    }

    public ModelList<Coluna> getCamposDisponiveis() {
        return camposDisponiveis;
    }

    public void setCamposDisponiveis(ModelList<Coluna> camposDisponiveis) {
        this.camposDisponiveis = camposDisponiveis;
    }

    public ModelList<Coluna> getCamposExibidos() {
        return camposExibidos;
    }

    public void setCamposExibidos(ModelList<Coluna> camposExibidos) {
        this.camposExibidos = camposExibidos;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public HashMap<Class, String> getMapPath() {
        return mapPath;
    }

    public void setMapPath(HashMap<Class, String> mapPath) {
        this.mapPath = mapPath;
    }

    public List<T> getRegistros() {
        return registros;
    }

    public void setRegistros(List<T> registros) {
        this.registros = registros;
    }

    public Class getClazz() {
        return clazz;
    }

    public TipoDeBusca getTipoDeBuscaSelecionada() {
        return tipoDeBuscaSelecionada;
    }

    public void setTipoDeBuscaSelecionada(TipoDeBusca tipoDeBuscaSelecionada) {
        this.tipoDeBuscaSelecionada = tipoDeBuscaSelecionada;
    }

    public List<TipoDeBusca> getTiposDeBusca() {
        if (campoSelecionado != null && (campoSelecionado.getClasseOriginal() == Date.class
                || campoSelecionado.getClasseOriginal() == Long.class
                || campoSelecionado.getClasseOriginal() == BigDecimal.class)) {
            return Arrays.asList(
                    TipoDeBusca.DIFERENTE_DE,
                    TipoDeBusca.IGUAL_A,
                    TipoDeBusca.MAIOR_OU_IGUAL_A,
                    TipoDeBusca.MAIOR_QUE,
                    TipoDeBusca.MENOR_OU_IGUAL_A,
                    TipoDeBusca.MENOR_QUE);

        } else if (campoSelecionado != null && campoSelecionado.getClasseOriginal() == String.class) {
            return Arrays.asList(
                    TipoDeBusca.CONTENDO,
                    TipoDeBusca.DIFERENTE_DE,
                    TipoDeBusca.IGUAL_A,
                    TipoDeBusca.INICIANDO,
                    TipoDeBusca.TERMINANDO);
        } else {
            return Arrays.asList(TipoDeBusca.values());
        }
    }

    public String getCampoSelecionadoString() {
        return campoSelecionadoString;
    }

    public void setCampoSelecionadoString(String campoSelecionadoString) {
        this.campoSelecionadoString = campoSelecionadoString;
    }

    public List<FilterModel> getFiltros() {
        return filtros;
    }

    public List<Totalizador> getTotalizadores() {
        return Arrays.asList(Totalizador.values());
    }

    public Date getConsultaDate() {
        if (consultaDate == null) {
            return Date.from(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).atZone(ZoneId.systemDefault()).toInstant());
        } else {
            return consultaDate;
        }
    }

    public void setConsultaDate(Date consultaDate) {
        this.consultaDate = consultaDate;
    }

    public boolean isDate() {
        return campoSelecionado == null ? false : campoSelecionado.getClasseOriginal() == Date.class;
    }

    public boolean isContainsMoeda() {
        return mapPath.containsKey(Moeda.class
        );
    }

    public Coluna getSiglaMoeda() {
        return siglaMoeda;
    }

    public Totalizador getTotalizador() {
        return totalizador;
    }

    public void setTotalizador(Totalizador totalizador) {
        this.totalizador = totalizador;
    }

    public List<Coluna> getColunasParaTotalizadores() {
        List<Coluna> camposParaTotalizadores = new ArrayList<>();

        for (Coluna c : camposExibidos.getList()) {
            if ((c.getClasseOriginal() == Long.class
                    || c.getClasseOriginal() == BigDecimal.class
                    || c.getClasseOriginal() == Integer.class
                    || c.getClasseOriginal() == Double.class)
                    && !getColunasTotalizadas().contains(c)) {
                camposParaTotalizadores.add(c);
            }
        }

        return camposParaTotalizadores;
    }

    public List<Coluna> getColunasTotalizadas() {
        return camposExibidos.getList().stream().filter((c) -> c.getTotalizador() != null).collect(Collectors.toList());
    }

    public String getColunaParaTotalizadorString() {
        return colunaParaTotalizadorString;
    }

    public void setColunaParaTotalizadorString(String colunaParaTotalizadorString) {
        this.colunaParaTotalizadorString = colunaParaTotalizadorString;
    }

    public Coluna getColunaParaTotalizadorSelecionada() {
        return colunaParaTotalizadorSelecionada;
    }

    public void setColunaParaTotalizadorSelecionada(Coluna colunaParaTotalizadorSelecionada) {
        this.colunaParaTotalizadorSelecionada = colunaParaTotalizadorSelecionada;
    }

    public String
            customFormat(Object obj) {

        if (obj.getClass() == BigDecimal.class) {

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMinimumFractionDigits(2);

            return numberFormat.format((BigDecimal) obj);

        } else if (obj.getClass() == Timestamp.class
                || obj.getClass() == Date.class) {
            Date date = (Date) obj;
            if (date != null) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.format(date);
            }
        }
        return obj.toString();
    }

    public List<T> getRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(List<T> registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

}
