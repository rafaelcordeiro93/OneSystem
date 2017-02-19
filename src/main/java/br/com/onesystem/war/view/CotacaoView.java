package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.CotacaoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class CotacaoView extends BasicMBImpl<Cotacao> implements Serializable {

    private CotacaoBV cotacao;
    private Cotacao cotacaoSelecionada;
    private Configuracao configuracao;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService serviceConfigurcao;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            Cotacao novoRegistro = cotacao.construir();
            new AdicionaDAO<Cotacao>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (cotacaoSelecionada != null) {
                Cotacao cotacaoExistente = cotacao.construirComID();
                new AtualizaDAO<Cotacao>(Cotacao.class).atualiza(cotacaoExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("cotacao_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (cotacaoSelecionada != null) {
                new RemoveDAO<Cotacao>(Cotacao.class).remove(cotacaoSelecionada, cotacaoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    @Override
    public void selecionar(SelectEvent e) {
        Object obj = e.getObject();
        if (obj instanceof Cotacao) {
            Cotacao a = (Cotacao) e.getObject();
            cotacao = new CotacaoBV(a);
            cotacaoSelecionada = a;
        } else if (obj instanceof Conta) {
            Conta conta = (Conta) obj;
            this.cotacao.setConta(conta);
        }
    }

    @Override
    public void buscaPorId() {
        Long id = cotacao.getId();
        if (id != null) {
            try {
                CotacaoDAO dao = new CotacaoDAO();
                Cotacao c = dao.buscarCotacoes().porId(id).resultado();
                cotacaoSelecionada = c;
                cotacao = new CotacaoBV(cotacaoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                cotacao.setId(id);
                die.print();
            }
        }
    }

    public void limparJanela() {
        cotacao = new CotacaoBV();
        cotacaoSelecionada = new Cotacao();
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

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

}
