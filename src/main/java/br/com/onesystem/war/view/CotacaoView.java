package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.CotacaoBV;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class CotacaoView implements Serializable {

    private boolean panel;
    private CotacaoBV cotacao;
    private Cotacao cotacaoSelecionada;
    private List<Cotacao> cotacaoLista;
    private List<Cotacao> cotacaosFiltradas;
    private Moeda moedaSelecionada;
    private List<Moeda> moedaLista;
    private List<Moeda> moedasFiltradas;

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

    @ManagedProperty("#{moedaService}")
    private MoedaService serviceMoeda;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        cotacaoLista = service.buscarCotacoes();
        moedaLista = serviceMoeda.buscarMoedas();
    }

    public void add() {
        try {
            Cotacao novoRegistro = cotacao.construir();
            new AdicionaDAO<Cotacao>().adiciona(novoRegistro);
            cotacaoLista.add(novoRegistro);
            InfoMessage.print("¡Cita de moneda'" + novoRegistro.getMoeda().getNome() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Cotacao cotacaoExistente = cotacao.construirComID();
            if (cotacaoExistente.getId() != null) {
                new AtualizaDAO<Cotacao>(Cotacao.class).atualiza(cotacaoExistente);
                cotacaoLista.set(cotacaoLista.indexOf(cotacaoExistente),
                        cotacaoExistente);
                if (cotacaosFiltradas != null && cotacaosFiltradas.contains(cotacaoExistente)) {
                    cotacaosFiltradas.set(cotacaosFiltradas.indexOf(cotacaoExistente), cotacaoExistente);
                }
                InfoMessage.print("¡Cita de moneda '" + cotacaoExistente.getMoeda().getNome() + "' cambiado con éxito!");
                limparJanela();

            } else {
                throw new EDadoInvalidoException("!La cita no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (cotacaoLista != null && cotacaoLista.contains(cotacaoSelecionada)) {
                new RemoveDAO<Cotacao>(Cotacao.class).remove(cotacaoSelecionada, cotacaoSelecionada.getId());
                cotacaoLista.remove(cotacaoSelecionada);
                if (cotacaosFiltradas != null && cotacaosFiltradas.contains(cotacaoSelecionada)) {
                    cotacaosFiltradas.remove(cotacaoSelecionada);
                }
                InfoMessage.print("Cita de moneda '" + this.cotacao.getMoeda().getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaMoeda() {
        if (moedaSelecionada != null) {
            cotacao.setMoeda(moedaSelecionada);
        }
    }

    public void limparJanela() {
        cotacao = new CotacaoBV();
        cotacaoSelecionada = new Cotacao();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        cotacao = new CotacaoBV(cotacaoSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }
    
    public void desfazer() {
        if (cotacaoSelecionada != null) {
            cotacao = new CotacaoBV(cotacaoSelecionada);
        }
    }

    public CotacaoBV getCotacao() {
        return cotacao;
    }

    public void setCotacao(CotacaoBV cotacao) {
        this.cotacao = cotacao;
    }

    public Cotacao getCotacaoSelecionada() {
        return cotacaoSelecionada;
    }

    public void setCotacaoSelecionada(Cotacao cotacaoSelecionada) {
        this.cotacaoSelecionada = cotacaoSelecionada;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public List<Cotacao> getCotacoesFiltradas() {
        return cotacaosFiltradas;
    }

    public void setCotacoesFiltradas(List<Cotacao> cotacaosFiltradas) {
        this.cotacaosFiltradas = cotacaosFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }

    public List<Cotacao> getCotacaosFiltradas() {
        return cotacaosFiltradas;
    }

    public void setCotacaosFiltradas(List<Cotacao> cotacaosFiltradas) {
        this.cotacaosFiltradas = cotacaosFiltradas;
    }

    public Moeda getMoedaSelecionada() {
        return moedaSelecionada;
    }

    public void setMoedaSelecionada(Moeda moedaSelecionada) {
        this.moedaSelecionada = moedaSelecionada;
    }

    public List<Moeda> getMoedaLista() {
        return moedaLista;
    }

    public void setMoedaLista(List<Moeda> moedaLista) {
        this.moedaLista = moedaLista;
    }

    public List<Moeda> getMoedasFiltradas() {
        return moedasFiltradas;
    }

    public void setMoedasFiltradas(List<Moeda> moedasFiltradas) {
        this.moedasFiltradas = moedasFiltradas;
    }

    public MoedaService getServiceMoeda() {
        return serviceMoeda;
    }

    public void setServiceMoeda(MoedaService serviceMoeda) {
        this.serviceMoeda = serviceMoeda;
    }

}
