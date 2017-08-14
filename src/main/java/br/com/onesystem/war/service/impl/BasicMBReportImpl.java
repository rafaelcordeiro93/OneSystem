/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service.impl;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.dao.ModeloDeRelatorioDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.services.impl.FormatacaoNumeroRelatorio;
import br.com.onesystem.util.ImpressoraDeRelatorioDinamico;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.ParametroDeFiltroDeRelatorio;
import br.com.onesystem.domain.builder.ModeloDeRelatorioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.valueobjects.TipoDeBusca;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javassist.Modifier;
import javax.inject.Named;
import net.sf.dynamicreports.report.exception.DRException;
import org.primefaces.event.ReorderEvent;
import org.reflections.Reflections;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.GeradorDeCodigoFonteDeModeloDeRelatorio;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import br.com.onesystem.war.builder.ColunaBV;
import br.com.onesystem.war.builder.FiltroDeRelatorioBV;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

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
    private List<FiltroDeRelatorio> filtros = new ArrayList<>();
    private Coluna campoSelecionado;
    private String campoSelecionadoString;
    private String colunaParaTotalizadorString;
    private Coluna colunaParaTotalizadorSelecionada;
    private TipoDeBusca tipoDeBuscaSelecionada = TipoDeBusca.CONTENDO;
    private Totalizador totalizador;
    private List<Model> modelDisponivelSelecionado = new ArrayList<>();
    private List<Coluna> colunaExibidaSelecionada = new LinkedList<>();
    protected List<T> registros = new ArrayList<>();
    protected List<T> registrosFiltrados = new ArrayList<>();
    private ModelList<Coluna> campos = new ModelList<Coluna>();
    private ModelList<Coluna> camposDisponiveis = new ModelList<Coluna>();
    private ModelList<Coluna> camposExibidos = new ModelList<Coluna>();
    private List<Class> classes = new ArrayList<>();
    private HashMap<Class, String> mapPath = new HashMap<>();
    protected BundleUtil bundle = new BundleUtil();
    private Coluna siglaMoeda;
    private TipoRelatorio tipoRelatorio;
    private String modeloDeRelatorioSelecionadoString;
    private List<ModeloDeRelatorio> modelosDeRelatorio;
    private List<String> enumeracoesSelecionadas = new ArrayList<>();
    private List<Enum> enumeracoesOpcoes = new ArrayList<>();
    private String codigoFonteModelo;

    protected abstract void init();

    /**
     * Deve obrigatóriamente chamar os métodos
     * inicializarRegistros,inicializarCampos
     *
     * @param clazz Classe.
     * @param dao Dao utilizado na classe
     * @param tipoRelatorio
     */
    protected void initialize(Class clazz, Class<? extends GenericDAO> dao, TipoRelatorio tipoRelatorio) {
        try {
            this.clazz = clazz;
            this.dao = dao;
            this.tipoRelatorio = tipoRelatorio;
            inicializarRegistros();
            inicializarCampos();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException cnf) {
            FatalMessage.print("Erro: BasicMBReport: Classe não encontrada!", cnf);
        }
    }

    private void inicializarRegistros() throws InstantiationException, IllegalAccessException {
        buscaDadosDoBancoComFiltros();
        modelosDeRelatorio = new ModeloDeRelatorioDAO().porTipoRelatorio(tipoRelatorio).listaDeResultados();
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
//            List<String> fieldsName = Arrays.asList(c.getDeclaredFields()).stream().map(Field::getName).collect(Collectors.toList());
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
                        addCampo(novoCampo);
                    } catch (MissingResourceException mre) {
                        String header = m.getName().substring(3);
                        header = "??" + header.substring(0, 1).toUpperCase() + header.substring(1) + "??";
                        if (c != clazz) {
                            header = header + " (" + table + ")";
                        }
                        Coluna novoCampo = new Coluna(header, table, property[0], property[1], property[2], property[3], c, m.getReturnType(), formatador);
                        addCampo(novoCampo);
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
        if (colunaExibidaSelecionada != null && !colunaExibidaSelecionada.isEmpty()) {
            for (Coluna c : colunaExibidaSelecionada) {
                camposDisponiveis.add(c);
                List<Model> list = new ArrayList<>();
                for (Model m : camposExibidos) {
                    Coluna col = (Coluna) m.getObject();
                    if (col.getPropriedadeCompleta().equals(c.getPropriedadeCompleta())) {
                        list.add(m);
                    }
                }
                if (!list.isEmpty()) {
                    list.forEach(m -> camposExibidos.remove(m));
                }
            }
            colunaExibidaSelecionada = new LinkedList<>();
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
        if (!camposExibidos.getList().contains(campo) && !camposDisponiveis.getList().contains(campo)) {
            if (!((campo.getNome() != null && campo.getNome().toLowerCase().contains("format"))
                    || (campo.getPropriedade() != null && campo.getPropriedade().toLowerCase().contains("format"))
                    || (campo.getPropriedadeDois() != null && campo.getPropriedadeDois().toLowerCase().contains("format"))
                    || (campo.getPropriedadeTres() != null && campo.getPropriedadeTres().toLowerCase().contains("format"))
                    || (campo.getPropriedadeQuatro() != null && campo.getPropriedadeQuatro().toLowerCase().contains("format")))) {
                this.camposDisponiveis.add(campo);
            }
        }
        if (!campos.getList().contains(campo)) {
            this.campos.add(campo);
        }
    }

    public void filterField() {
        for (Model c : campos) {
            if (((Coluna) c.getObject()).getNome().equals(campoSelecionadoString)) {
                campoSelecionado = (Coluna) c.getObject();
                if (campoSelecionado.getClasseOriginal().isEnum()) {
                    enumeracoesSelecionadas = new ArrayList<>();
                    enumeracoesOpcoes = new ArrayList<>();
                    for (Object o : campoSelecionado.getClasseOriginal().getEnumConstants()) {
                        Enum e = (Enum) o;
                        enumeracoesOpcoes.add(e);
                    }
                }
            }
        }
    }

    public void filterColumn() {
        for (Model c : camposExibidos) {
            if (((Coluna) c.getObject()).getNome().equals(colunaParaTotalizadorString)) {
                colunaParaTotalizadorSelecionada = (Coluna) c.getObject();
            }
        }
    }

    public void delTotalizador(Coluna coluna) {
        // Deleta totalizador na coluna
        camposExibidos.getList().get(camposExibidos.getList().indexOf(coluna)).setTotalizador(null);
    }

    public void delFilter(FiltroDeRelatorio filter) {
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
        Model model = null;
        for (Model m : camposExibidos) {
            Coluna col = (Coluna) m.getObject();
            if (col.getPropriedadeCompleta().equals(colunaParaTotalizadorSelecionada.getPropriedadeCompleta())) {
                col.setTotalizador(totalizador);
                m.setObject(col);
                model = m;
            }
        }
        if (model != null) {
            camposExibidos.set(model);
        }
    }

    public void pesquisar() {
        try {
            if (campoSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Selecione_Um_Campo_Para_Filtrar"));
            } else if ((consulta == null || consulta.isEmpty()) && consultaDate == null && (enumeracoesSelecionadas == null || enumeracoesSelecionadas.isEmpty())) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Informe_Filtro_Pesquisa"));
            } else {

                //Adiciona os filtros
                FiltroDeRelatorio filtro = new FiltroDeRelatorio(null, new ColunaBV(campoSelecionado).construir(), tipoDeBuscaSelecionada);

                if (campoSelecionado.getClasseOriginal() != Date.class) {

                    if (consulta != null && !consulta.isEmpty()) {
                        // Faz o tratamento dos filtros em String para seus devidos Tipos.
                        for (String s : consulta) {
                            //LONG ===============================================================================
                            if (campoSelecionado.getClasseOriginal() == Long.class && !filtros.contains(filtro)) {
                                filtro.add(new Long(s));
                            } else if (campoSelecionado.getClasseOriginal() == Long.class && filtros.contains(filtro)) {
                                filtros.get(filtros.indexOf(filtro)).add(new Long(s));
                            } else //Integer ===============================================================================
                            if (campoSelecionado.getClasseOriginal() == Integer.class && !filtros.contains(filtro)) {
                                filtro.add(new Integer(s));
                            } else if (campoSelecionado.getClasseOriginal() == Integer.class && filtros.contains(filtro)) {
                                filtros.get(filtros.indexOf(filtro)).add(new Integer(s));
                            } //BIGDECIMAL =======================================================================
                            else if (campoSelecionado.getClasseOriginal() == BigDecimal.class && !filtros.contains(filtro)) {
                                s = s.replaceAll(",", ".");
                                filtro.add(new BigDecimal(s));
                            } else if (campoSelecionado.getClasseOriginal() == BigDecimal.class && filtros.contains(filtro)) {
                                s = s.replaceAll(",", ".");
                                filtros.get(filtros.indexOf(filtro)).add(new BigDecimal(s));
                            } //STRING ============================================================================
                            else if (campoSelecionado.getClasseOriginal() == String.class && !filtros.contains(filtro)) {
                                filtro.add(s.toLowerCase()); //Deve ser lower case devido a consulta sql.
                            } else if (campoSelecionado.getClasseOriginal() == String.class && filtros.contains(filtro)) {
                                filtros.get(filtros.indexOf(filtro)).add(s.toLowerCase());//Deve ser lower case devido a consulta sql.
                            }
                        }
                    }

                    if (enumeracoesSelecionadas != null && !enumeracoesSelecionadas.isEmpty()) {
                        // Faz o tratamento para os filtros de Enum
                        for (String s : enumeracoesSelecionadas) {
                            //Enum ====================================================================================
                            if (!filtros.contains(filtro)) {
                                filtro.add(filtro.getEnum(s));
                            } else if (filtros.contains(filtro)) {
                                filtros.get(filtros.indexOf(filtro)).add(filtro.getEnum(s));
                            }
                        }
                    }

                    if (!filtro.getFiltros().isEmpty()) {
                        filtros.add(filtro);
                    }
                } else if (campoSelecionado.getClasseOriginal() == Date.class) {
                    //DATE ====================================================================================
                    if (filtros.contains(filtro)) {
                        filtros.get(filtros.indexOf(filtro)).setFiltroDeData(getConsultaDate());
                    } else {
                        filtros.add(new FiltroDeRelatorio(null, campoSelecionado, getConsultaDate(), tipoDeBuscaSelecionada));
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
                for (FiltroDeRelatorio f : filtros) {
                    if (f.getFiltroDeData() == null) {
                        gDao.filter(f.getTipoDaBusca(), f.getColuna(), f.getFiltros());
                    } else {
                        gDao.filter(f.getTipoDaBusca(), f.getColuna(), f.getFiltroDeData());
                    }
                }
            }

            registros = gDao.listaDeResultados();
            registrosFiltrados = registros;

        } catch (InstantiationException | IllegalAccessException ex) {
            FatalMessage.print("Erro de acesso ao inicializar dao chips: " + ex.getMessage(), ex);
            Logger.getLogger(BasicMBReportImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void alteraConsulta(GenericDAO gDao) {
    }

    public void imprimir() {
        ImpressoraDeRelatorioDinamico impressora = new ImpressoraDeRelatorioDinamico();
        String nomeRelatorio = modeloDeRelatorioSelecionadoString != null && !modeloDeRelatorioSelecionadoString.trim().isEmpty() ? modeloDeRelatorioSelecionadoString : tipoRelatorio.getNome();
        try {
            if (registrosFiltrados == null || registrosFiltrados.isEmpty()) {
                impressora.imprimir(registros, nomeRelatorio, camposExibidos.getList(), mapPath.get(Moeda.class)).naWeb();
            } else {
                impressora.imprimir(registrosFiltrados, nomeRelatorio, camposExibidos.getList(), mapPath.get(Moeda.class)).naWeb();
            }
        } catch (DRException | IOException | FDadoInvalidoException ex) {
            Logger.getLogger(BasicMBReportImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvarModeloDeRelatorio() {
        try {
            if (modeloDeRelatorioSelecionadoString != null && !modeloDeRelatorioSelecionadoString.trim().isEmpty()) {

                ModeloDeRelatorio modeloRemovido = null; // * Necessario para não soltar CuncurrentException

                //Exclui o  modelo
                for (ModeloDeRelatorio m : modelosDeRelatorio) {
                    if (m.getNome().equals(modeloDeRelatorioSelecionadoString)) {
                        ModeloDeRelatorio find = new ArmazemDeRegistros<>(ModeloDeRelatorio.class).find(m.getId());
                        new RemoveDAO<>().remove(find, find.getId());
                        modeloRemovido = m; // * Necessario para não soltar CuncurrentException
                        break;
                    }
                }

                // * Necessario para não soltar CuncurrentException
                if (modeloRemovido != null) {
                    modelosDeRelatorio.remove(modeloRemovido);
                }

                //Cria novo modelo
                ModeloDeRelatorio modelo = criarModelo();

                //Adiciona no banco novo registro
                new AdicionaDAO<>().adiciona(modelo);
                modelosDeRelatorio.add(modelo);

                InfoMessage.adicionado();
            }
        } catch (DadoInvalidoException ex) {
            ex.printConsole();
            ex.print();
        }
    }

    public ModeloDeRelatorio criarModelo() throws DadoInvalidoException {
        ModeloDeRelatorio modelo = new ModeloDeRelatorioBuilder().comNome(modeloDeRelatorioSelecionadoString).comTipoRelatorio(tipoRelatorio).construir();

        //Adiciona Colunas
        for (Coluna c : camposExibidos.getList()) {
            Coluna coluna = new ColunaBV(c).construir();
            modelo.addColunaExibida(coluna);
        }

        //Adiciona Filtros
        for (FiltroDeRelatorio f : filtros) {
            FiltroDeRelatorio filtro = new FiltroDeRelatorioBV(f).construir();
            //Adiciona filtro dentro dos parametros - Cascade
            for (ParametroDeFiltroDeRelatorio p : filtro.getParametros()) {
                p.setId(null);
                p.setFiltroDeRelatorio(filtro);
            }
            //Altera coluna do filtro para que não tenha modelo e ser persistida como filtro.
            Coluna col = new ColunaBV(filtro.getColuna()).construir();
            col.setModelo(null);
            filtro.setColuna(col);

            modelo.addFiltro(filtro);
        }
        return modelo;
    }

    public void selecionaModelo() {
        if (modeloDeRelatorioSelecionadoString != null && !modeloDeRelatorioSelecionadoString.trim().isEmpty()) {

            for (ModeloDeRelatorio m : modelosDeRelatorio) {
                if (m.getNome().equals(modeloDeRelatorioSelecionadoString)) {
                    limpaFiltrosECampos();
                    //Adiciona Filtros
                    if (m.getFiltroDeRelatorio() != null) {
                        for (FiltroDeRelatorio f : m.getFiltroDeRelatorio()) {
                            filtros.add(f);
                        }
                    }

                    //Adiciona Colunas
                    if (m.getColunasExibidas() != null) {
                        for (Coluna c : m.getColunasExibidas()) {
                            for (Model model : camposDisponiveis) {
                                if (((Coluna) model.getObject()).getPropriedadeCompleta().equalsIgnoreCase(c.getPropriedadeCompleta())) {
                                    camposDisponiveis.remove(model);
                                    camposExibidos.add(c);
                                    break;
                                }
                            }
                        }
                    }

                    buscaDadosDoBancoComFiltros();
                    break;
                }
            }
        }
    }

    private void limpaFiltrosECampos() {
        camposExibidos.forEach(c -> camposDisponiveis.add((Coluna) c.getObject()));
        camposExibidos = new ModelList<>();
        filtros = new ArrayList<>();
    }

    public void excluirModelo() {
        try {
            if (modeloDeRelatorioSelecionadoString != null && !modeloDeRelatorioSelecionadoString.trim().isEmpty()) {

                //Exclui modelo existente
                for (ModeloDeRelatorio m : modelosDeRelatorio) {
                    if (m.getNome().equals(modeloDeRelatorioSelecionadoString)) {
                        ModeloDeRelatorio find = new ArmazemDeRegistros<>(ModeloDeRelatorio.class).find(m.getId());
                        new RemoveDAO<>().remove(find, find.getId());
                        limpaFiltrosECampos();
                        init();
                        //Limpa o campo do modelo inicializa campos padros e busca dados do banco.
                        modeloDeRelatorioSelecionadoString = "";
                        buscaDadosDoBancoComFiltros();
                        InfoMessage.removido();
                        break;
                    }
                }
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void gerarCodigoFonte() {
        try {
            //Limpa variável
            codigoFonteModelo = null;
            //Cria novo modelo
            ModeloDeRelatorio modelo = criarModelo();
            if (modelo != null) {
                GeradorDeCodigoFonteDeModeloDeRelatorio gerador = new GeradorDeCodigoFonteDeModeloDeRelatorio();
                codigoFonteModelo = gerador.gerarCodigo(modelo);
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public String getCodigoFonteModelo() {
        return codigoFonteModelo;
    }

    public void setCodigoFonteModelo(String codigoFonteModelo) {
        this.codigoFonteModelo = codigoFonteModelo;
    }

    public void limparJanela() {
        modeloDeRelatorioSelecionadoString = "";
        codigoFonteModelo = null;
        limpaFiltrosECampos();
        init();
        buscaDadosDoBancoComFiltros();
    }

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

    public List<Coluna> getColunaExibidaSelecionada() {
        return colunaExibidaSelecionada;
    }

    public void setColunaExibidaSelecionada(List<Coluna> colunaExibidaSelecionada) {
        this.colunaExibidaSelecionada = colunaExibidaSelecionada;
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
                    TipoDeBusca.IGUAL_A,
                    TipoDeBusca.DIFERENTE_DE,
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
        } else if (campoSelecionado != null && campoSelecionado.getClasseOriginal().isEnum()) {
            return Arrays.asList(
                    TipoDeBusca.IGUAL_A,
                    TipoDeBusca.DIFERENTE_DE);
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

    public List<FiltroDeRelatorio> getFiltros() {
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

    public int getTypeField() {
        if (campoSelecionado == null) {
            return 1;
        } else if (campoSelecionado.getClasseOriginal() == Date.class) {
            return 2;
        } else if (campoSelecionado.getClasseOriginal().isEnum()) {
            return 3;
        } else {
            return 1;
        }
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

    public void onResizeColumnObjects(Object objeto, Object tamanho) {
        Coluna col = (Coluna) objeto;
        Integer i = Integer.valueOf((String) tamanho);
        col.setTamanho(i);

        for (Model m : camposExibidos) {
            Coluna colModel = (Coluna) m.getObject();
            if (colModel.getNome().equals(col.getNome())) {
                m.setObject(col);
                camposExibidos.set(m);
                break;
            }
        }
    }

    public Object customFormat(Object obj) {
        try {
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
            } else if (obj.getClass().isEnum()) {
                Enum o = (Enum) obj;
                return o.getClass().getMethod("getNome", null).invoke(o, null).toString();
            }
            return obj;
        } catch (Exception ex) {
            return obj;
        }
    }

    public List<T> getRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(List<T> registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

    public List<ModeloDeRelatorio> getModelosDeRelatorio() {
        return modelosDeRelatorio;
    }

    public String getModeloDeRelatorioSelecionadoString() {
        return modeloDeRelatorioSelecionadoString;
    }

    public void setModeloDeRelatorioSelecionadoString(String modeloDeRelatorioSelecionadoString) {
        this.modeloDeRelatorioSelecionadoString = modeloDeRelatorioSelecionadoString;
    }

    public List<String> getEnumeracoesSelecionadas() {
        return enumeracoesSelecionadas;
    }

    public List<Enum> getEnumeracoesOpcoes() {
        return enumeracoesOpcoes;
    }

    public void setEnumeracoesSelecionadas(List<String> enumeracoesSelecionadas) {
        this.enumeracoesSelecionadas = enumeracoesSelecionadas;
    }

}
