package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.war.builder.ReceitaProvisionadaBV;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
import java.io.Serializable;
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
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ReceitaProvisionadaView implements Serializable {

    private boolean panel;
    private ReceitaProvisionadaBV receitaProvisionada;
    private ReceitaProvisionada receitaProvisionadaSelecionado;
    private List<ReceitaProvisionada> receitaProvisionadaLista;
    private List<ReceitaProvisionada> gruposFinanceirosFiltrados;
    private List<ReceitaProvisionadaBV> parcelas;
    private Integer numeroParcelas;
    private Integer intervaloDias;

    @ManagedProperty("#{receitaProvisionadaService}")
    private ReceitaProvisionadaService service;

    @ManagedProperty("#{moedaService}")
    private MoedaService serviceMoeda;

    private List<Moeda> moedaLista;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        receitaProvisionada = new ReceitaProvisionadaBV();
        receitaProvisionadaLista = service.buscarReceitaProvisionadas();
        moedaLista = serviceMoeda.buscarMoedas();
    }

    public void add() {
        try {
            ReceitaProvisionada novoRegistro = receitaProvisionada.construir();
            new AdicionaDAO<ReceitaProvisionada>().adiciona(novoRegistro);
            receitaProvisionadaLista.add(novoRegistro);
            for (ReceitaProvisionadaBV n : parcelas) {
                novoRegistro = n.construir();
                new AdicionaDAO<ReceitaProvisionada>().adiciona(novoRegistro);
                receitaProvisionadaLista.add(novoRegistro);
            }
            InfoMessage.print("Receita Provisionada agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            ReceitaProvisionada receitaProvisionadaExistente = receitaProvisionada.construirComID();
            if (receitaProvisionadaExistente.getId() != null) {
                new AtualizaDAO<ReceitaProvisionada>(ReceitaProvisionada.class).atualiza(receitaProvisionadaExistente);
                receitaProvisionadaLista.set(receitaProvisionadaLista.indexOf(receitaProvisionadaExistente),
                        receitaProvisionadaExistente);
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(receitaProvisionadaExistente)) {
                    gruposFinanceirosFiltrados.set(gruposFinanceirosFiltrados.indexOf(receitaProvisionadaExistente), receitaProvisionadaExistente);
                }
                InfoMessage.print("Receita Provisionada cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La receitaProvisionada no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (receitaProvisionadaLista != null && receitaProvisionadaLista.contains(receitaProvisionada.construirComID())) {
                new RemoveDAO<ReceitaProvisionada>(ReceitaProvisionada.class).remove(receitaProvisionada.construirComID(), receitaProvisionada.construirComID().getId());
                receitaProvisionadaLista.remove(receitaProvisionada.construirComID());
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(receitaProvisionada.construirComID())) {
                    gruposFinanceirosFiltrados.remove(receitaProvisionada.construirComID());
                }
                InfoMessage.print("ReceitaProvisionada '" + this.receitaProvisionada.getId() + "' eliminada con éxito!");
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
            parcelas = new ArrayList<ReceitaProvisionadaBV>();
            Integer count = new Integer(0);
            for (int i = 0; i < numeroParcelas; i++) {
                count = count + intervaloDias;
                ReceitaProvisionadaBV dp
                        = new ReceitaProvisionadaBV(retornarCodigo(),
                                receitaProvisionada.getPessoa(),
                                receitaProvisionada.getReceita(),
                                receitaProvisionada.getValor(),
                                adicionarDiasNa(receitaProvisionada.getVencimento(), count),
                                receitaProvisionada.getEmissao(),
                                receitaProvisionada.getHistorico(),
                                receitaProvisionada.getCotacao());
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
            for (ReceitaProvisionadaBV dp : parcelas) {
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
        receitaProvisionada = new ReceitaProvisionadaBV();
        receitaProvisionadaSelecionado = new ReceitaProvisionada();
        intervaloDias = null;
        numeroParcelas = null;
        parcelas = new ArrayList<ReceitaProvisionadaBV>();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        receitaProvisionada = new ReceitaProvisionadaBV(receitaProvisionadaSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (receitaProvisionadaSelecionado != null) {
            receitaProvisionada = new ReceitaProvisionadaBV(receitaProvisionadaSelecionado);
        }
    }

    public void selecionarMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
//        receitaProvisionada.setMoeda(moeda);
    }

    public ReceitaProvisionadaBV getReceitaProvisionada() {
        return receitaProvisionada;
    }

    public void setReceitaProvisionada(ReceitaProvisionadaBV receitaProvisionada) {
        this.receitaProvisionada = receitaProvisionada;
    }

    public ReceitaProvisionada getReceitaProvisionadaSelecionado() {
        return receitaProvisionadaSelecionado;
    }

    public void setReceitaProvisionadaSelecionado(ReceitaProvisionada receitaProvisionadaSelecionado) {
        this.receitaProvisionadaSelecionado = receitaProvisionadaSelecionado;
    }

    public List<ReceitaProvisionada> getReceitaProvisionadaLista() {
        return receitaProvisionadaLista;
    }

    public void setReceitaProvisionadaLista(List<ReceitaProvisionada> receitaProvisionadaLista) {
        this.receitaProvisionadaLista = receitaProvisionadaLista;
    }

    public List<ReceitaProvisionada> getGruposFinanceirosFiltrados() {
        return gruposFinanceirosFiltrados;
    }

    public void setGruposFinanceirosFiltrados(List<ReceitaProvisionada> gruposFinanceirosFiltrados) {
        this.gruposFinanceirosFiltrados = gruposFinanceirosFiltrados;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public ReceitaProvisionadaService getService() {
        return service;
    }

    public void setService(ReceitaProvisionadaService service) {
        this.service = service;
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        receitaProvisionada.setPessoa(pessoaSelecionada);
    }

    public void selecionaReceita(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        receitaProvisionada.setReceita(receitaSelecionada);
    }

    public List<ReceitaProvisionadaBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ReceitaProvisionadaBV> parcelas) {
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
