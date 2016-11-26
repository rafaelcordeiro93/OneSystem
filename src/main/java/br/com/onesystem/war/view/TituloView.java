package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.war.builder.TituloBV;
import br.com.onesystem.war.service.TituloService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class TituloView implements Serializable {

    private boolean panel;
    private TituloBV titulo;
    private Titulo tituloSelecionado;
    private List<Titulo> tituloLista;
    private List<Titulo> gruposFinanceirosFiltrados;
    private List<TituloBV> parcelas;
    private Integer numeroParcelas;
    private Integer intervaloDias;

    @ManagedProperty("#{tituloService}")
    private TituloService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        titulo = new TituloBV();
        tituloLista = service.buscarTitulos();
    }

    public void add() {
        try {
            Titulo novoRegistro = titulo.construir();
            new AdicionaDAO<Titulo>().adiciona(novoRegistro);
            tituloLista.add(novoRegistro);
            for(TituloBV n : parcelas){
                novoRegistro = n.construir();
                new AdicionaDAO<Titulo>().adiciona(novoRegistro);
                tituloLista.add(novoRegistro);
            }            
            InfoMessage.print("Titulo Provisionada agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Titulo tituloExistente = titulo.construirComID();
            if (tituloExistente.getId() != null) {
                new AtualizaDAO<Titulo>(Titulo.class).atualiza(tituloExistente);
                tituloLista.set(tituloLista.indexOf(tituloExistente),
                        tituloExistente);
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(tituloExistente)) {
                    gruposFinanceirosFiltrados.set(gruposFinanceirosFiltrados.indexOf(tituloExistente), tituloExistente);
                }
                InfoMessage.print("Titulo Provisionada cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La titulo no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (tituloLista != null && tituloLista.contains(titulo.construirComID())) {
                new RemoveDAO<Titulo>(Titulo.class).remove(titulo.construirComID(), titulo.construirComID().getId());
                tituloLista.remove(titulo.construirComID());
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(titulo.construirComID())) {
                    gruposFinanceirosFiltrados.remove(titulo.construirComID());
                }
                InfoMessage.print("Titulo '" + this.titulo.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void gerarMaisParcelas() {
        try {
            parcelas = new ArrayList<TituloBV>();
            Integer count = new Integer(0);
//            for (int i = 0; i < numeroParcelas; i++) {
//                count = count + intervaloDias;
//                TituloBV dp
//                        = new TituloBV(retornarCodigo(),
//                                titulo.getPessoa(),
//                                titulo.getCambio(),
//                                titulo.getValor(),
//                                adicionarDiasNa(titulo.getVencimento(), count),
//                                titulo.getEmissao(),
//                                titulo.getHistorico(),
//                                false);
//                parcelas.add(dp);
//            }
        } catch (NullPointerException npe) {
            ErrorMessage.print(new BundleUtil().getMessage("Vencimento_idias_nparcelas_not_null"));
        }

    }

    private Date adicionarDiasNa(Date data, Integer dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, dias);
        return c.getTime();
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!parcelas.isEmpty()) {
            for (TituloBV dp : parcelas) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public List<NaturezaFinanceira> getNaturezasFinanceiras() {
        return Arrays.asList(NaturezaFinanceira.values());
    }

    public List<ClassificacaoFinanceira> getClassificacaoFinanceira() {
        return Arrays.asList(ClassificacaoFinanceira.values());
    }

    public void limparJanela() {
        titulo = new TituloBV();
        tituloSelecionado = new Titulo();
        intervaloDias = null;
        numeroParcelas = null;
        parcelas = new ArrayList<TituloBV>();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        titulo = new TituloBV(tituloSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (tituloSelecionado != null) {
            titulo = new TituloBV(tituloSelecionado);
        }
    }

    public TituloBV getTitulo() {
        return titulo;
    }

    public void setTitulo(TituloBV titulo) {
        this.titulo = titulo;
    }

    public Titulo getTituloSelecionado() {
        return tituloSelecionado;
    }

    public void setTituloSelecionado(Titulo tituloSelecionado) {
        this.tituloSelecionado = tituloSelecionado;
    }

    public List<Titulo> getTituloLista() {
        return tituloLista;
    }

    public void setTituloLista(List<Titulo> tituloLista) {
        this.tituloLista = tituloLista;
    }

    public List<Titulo> getGruposFinanceirosFiltrados() {
        return gruposFinanceirosFiltrados;
    }

    public void setGruposFinanceirosFiltrados(List<Titulo> gruposFinanceirosFiltrados) {
        this.gruposFinanceirosFiltrados = gruposFinanceirosFiltrados;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public TituloService getService() {
        return service;
    }

    public void setService(TituloService service) {
        this.service = service;
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        titulo.setPessoa(pessoaSelecionada);
    }

    public void selecionaRecepcao(SelectEvent event) {
        Recepcao recepcaoSelecionada = (Recepcao) event.getObject();
        titulo.setRecepcao(recepcaoSelecionada);
    }
    
    public void selecionaCambio(SelectEvent event) {
        Cambio cambioSelecionado = (Cambio) event.getObject();
        titulo.setCambio(cambioSelecionado);
    }

    public List<TituloBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<TituloBV> parcelas) {
        this.parcelas = parcelas;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Integer getIntervaloDias() {
        return intervaloDias;
    }

    public void setIntervaloDias(Integer intervaloDias) {
        this.intervaloDias = intervaloDias;
    }

}
