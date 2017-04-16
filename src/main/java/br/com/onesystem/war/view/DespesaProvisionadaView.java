package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.war.builder.DespesaProvisionadaBV;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.MoedaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DespesaProvisionadaView implements Serializable {

    private boolean panel;
    private DespesaProvisionadaBV despesaProvisionada;
    private DespesaProvisionada despesaProvisionadaSelecionado;
    private List<DespesaProvisionada> despesaProvisionadaLista;
    private List<DespesaProvisionada> gruposFinanceirosFiltrados;
    private List<DespesaProvisionadaBV> parcelas;
    private Integer numeroParcelas;
    private Integer intervaloDias;

    @Inject
    private DespesaProvisionadaService service;

    @Inject
    private MoedaService serviceMoeda;

    private List<Moeda> moedaLista;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        despesaProvisionada = new DespesaProvisionadaBV();
        despesaProvisionadaLista = service.buscarDespesaProvisionadas();
        moedaLista = serviceMoeda.buscarMoedas();
    }

    public void add() {
        try {
            DespesaProvisionada novoRegistro = despesaProvisionada.construir();
            new AdicionaDAO<DespesaProvisionada>().adiciona(novoRegistro);
            despesaProvisionadaLista.add(novoRegistro);
            for (DespesaProvisionadaBV n : parcelas) {
                novoRegistro = n.construir();
                new AdicionaDAO<DespesaProvisionada>().adiciona(novoRegistro);
                despesaProvisionadaLista.add(novoRegistro);
            }
            InfoMessage.print("Despesa Provisionada agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            DespesaProvisionada despesaProvisionadaExistente = despesaProvisionada.construirComID();
            if (despesaProvisionadaExistente.getId() != null) {
                new AtualizaDAO<DespesaProvisionada>().atualiza(despesaProvisionadaExistente);
                despesaProvisionadaLista.set(despesaProvisionadaLista.indexOf(despesaProvisionadaExistente),
                        despesaProvisionadaExistente);
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(despesaProvisionadaExistente)) {
                    gruposFinanceirosFiltrados.set(gruposFinanceirosFiltrados.indexOf(despesaProvisionadaExistente), despesaProvisionadaExistente);
                }
                InfoMessage.print("Despesa Provisionada cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La despesaProvisionada no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (despesaProvisionadaLista != null && despesaProvisionadaLista.contains(despesaProvisionada.construirComID())) {
                new RemoveDAO<DespesaProvisionada>().remove(despesaProvisionada.construirComID(), despesaProvisionada.construirComID().getId());
                despesaProvisionadaLista.remove(despesaProvisionada.construirComID());
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(despesaProvisionada.construirComID())) {
                    gruposFinanceirosFiltrados.remove(despesaProvisionada.construirComID());
                }
                InfoMessage.print("DespesaProvisionada '" + this.despesaProvisionada.getId() + "' eliminada con éxito!");
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
            parcelas = new ArrayList<DespesaProvisionadaBV>();
            Integer count = new Integer(0);
            for (int i = 0; i < numeroParcelas; i++) {
                count = count + intervaloDias;
                DespesaProvisionadaBV dp
                        = new DespesaProvisionadaBV(retornarCodigo(),
                                despesaProvisionada.getPessoa(),
                                despesaProvisionada.getDespesa(),
                                despesaProvisionada.getValor(),
                                adicionarDiasNa(despesaProvisionada.getVencimento(), count),
                                despesaProvisionada.getEmissao(),
                                despesaProvisionada.getHistorico(),
                                false,
                                despesaProvisionada.getCotacao(),
                                null,
                                null);
                parcelas.add(dp);
            }
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
            for (DespesaProvisionadaBV dp : parcelas) {
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
        despesaProvisionada = new DespesaProvisionadaBV();
        despesaProvisionadaSelecionado = new DespesaProvisionada();
        intervaloDias = null;
        numeroParcelas = null;
        parcelas = new ArrayList<DespesaProvisionadaBV>();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void selecionarMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
//        despesaProvisionada.setMoeda(moeda);
    }

    public void abrirEdicaoComDados() {
        panel = true;
        despesaProvisionada = new DespesaProvisionadaBV(despesaProvisionadaSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (despesaProvisionadaSelecionado != null) {
            despesaProvisionada = new DespesaProvisionadaBV(despesaProvisionadaSelecionado);
        }
    }

    public DespesaProvisionadaBV getDespesaProvisionada() {
        return despesaProvisionada;
    }

    public void setDespesaProvisionada(DespesaProvisionadaBV despesaProvisionada) {
        this.despesaProvisionada = despesaProvisionada;
    }

    public DespesaProvisionada getDespesaProvisionadaSelecionado() {
        return despesaProvisionadaSelecionado;
    }

    public void setDespesaProvisionadaSelecionado(DespesaProvisionada despesaProvisionadaSelecionado) {
        this.despesaProvisionadaSelecionado = despesaProvisionadaSelecionado;
    }

    public List<DespesaProvisionada> getDespesaProvisionadaLista() {
        return despesaProvisionadaLista;
    }

    public void setDespesaProvisionadaLista(List<DespesaProvisionada> despesaProvisionadaLista) {
        this.despesaProvisionadaLista = despesaProvisionadaLista;
    }

    public List<DespesaProvisionada> getGruposFinanceirosFiltrados() {
        return gruposFinanceirosFiltrados;
    }

    public void setGruposFinanceirosFiltrados(List<DespesaProvisionada> gruposFinanceirosFiltrados) {
        this.gruposFinanceirosFiltrados = gruposFinanceirosFiltrados;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public DespesaProvisionadaService getService() {
        return service;
    }

    public void setService(DespesaProvisionadaService service) {
        this.service = service;
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        despesaProvisionada.setPessoa(pessoaSelecionada);
    }

    public void selecionaDespesa(SelectEvent event) {
        Despesa despesaSelecionada = (Despesa) event.getObject();
        despesaProvisionada.setDespesa(despesaSelecionada);
    }

    public List<DespesaProvisionadaBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<DespesaProvisionadaBV> parcelas) {
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

    public List<Moeda> getMoedaLista() {
        return moedaLista;
    }

    public void setMoedaLista(List<Moeda> moedaLista) {
        this.moedaLista = moedaLista;
    }

    public MoedaService getServiceMoeda() {
        return serviceMoeda;
    }

    public void setServiceMoeda(MoedaService serviceMoeda) {
        this.serviceMoeda = serviceMoeda;
    }

}
