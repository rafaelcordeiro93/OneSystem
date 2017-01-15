package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ChequeView implements Serializable {

    private ChequeBV cheque;
    private Cheque chequeSelecionada;
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
            Cheque novoRegistro = cheque.construir();

            new AdicionaDAO<Cheque>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (chequeSelecionada != null) {
                Cheque chequeExistente = cheque.construirComID();

                new AtualizaDAO<Cheque>(Cheque.class).atualiza(chequeExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("cheque_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (chequeSelecionada != null) {
                new RemoveDAO<Cheque>(Cheque.class).remove(chequeSelecionada, chequeSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaVenda(SelectEvent event) {
        NotaEmitida notaSelecionado = (NotaEmitida) event.getObject();
        cheque.setVenda(notaSelecionado);
    }

    public void selecionaBanco(SelectEvent event) {
        Banco bancoSelecionado = (Banco) event.getObject();
        cheque.setBanco(bancoSelecionado);
    }

    public void selecionaCheque(SelectEvent e) {
        Cheque a = (Cheque) e.getObject();
        cheque = new ChequeBV(a);
        chequeSelecionada = a;
    }

    public List<SituacaoDeCheque> getTipoSituacao() {
        return Arrays.asList(SituacaoDeCheque.values());
    }

    public void limparJanela() {
        cheque = new ChequeBV();
        chequeSelecionada = null;
    }

    public void desfazer() {
        if (chequeSelecionada != null) {
            cheque = new ChequeBV(chequeSelecionada);
        }
    }

    public ChequeBV getCheque() {
        return cheque;
    }

    public void setCheque(ChequeBV cheque) {
        this.cheque = cheque;
    }

    public Cheque getChequeSelecionada() {
        return chequeSelecionada;
    }

    public void setChequeSelecionada(Cheque chequeSelecionada) {
        this.chequeSelecionada = chequeSelecionada;
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
