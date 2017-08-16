package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ChequeView extends BasicMBImpl<Cheque, ChequeBV> implements Serializable {

    private Configuracao configuracao;
    private List<Cotacao> listaCotacao;

    @Inject
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
            setOperacaoFinanceira();
            Cheque ch = e.construir();
            ch.geraBaixaDeCheque();
            new AdicionaDAO<>().adiciona(ch);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            setOperacaoFinanceira();
            Cheque ch = e.construirComID();
            new AtualizaDAO<>().atualiza(ch);
            if (ch.getCompensacao() != null && ch.getEstadoDeCheque() == EstadoDeCheque.COMPENSADO) {
                atualizaBaixasCompensação(ch);
            } else if (ch.getEstadoDeCheque() == EstadoDeCheque.CANCELADO) {
                atualizaBaixasCancelado(ch);
            } else if (ch.getEstadoDeCheque() == EstadoDeCheque.DESCONTADO) {
                atualizaBaixasDescontado(ch);
            } else if (ch.getEstadoDeCheque() == EstadoDeCheque.ABERTO || ch.getEstadoDeCheque() == EstadoDeCheque.DEVOLVIDO) {
                atualizaBaixasEmAberto(ch);
            }
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void atualizaBaixasCompensação(Cheque d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = new BaixaDAO().ePorCobranca(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            bv.setOperacaoFinanceira(d.getOperacaoFinanceira());
            bv.setDataCompensacao(d.getCompensacao());
            bv.setDataCancelamento(null);
            bv.setEstado(EstadoDeBaixa.EFETIVADO);
            new AtualizaDAO<>().atualiza(bv.construirComID());
        }
    }

    private void atualizaBaixasCancelado(Cheque d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = new BaixaDAO().ePorCobranca(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            bv.setDataCompensacao(null);
            Baixa a = bv.construirComID();
            a.cancela();
            new AtualizaDAO<>().atualiza(a);
        }
    }

    private void atualizaBaixasDescontado(Cheque d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = new BaixaDAO().ePorCobranca(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            bv.setOperacaoFinanceira(d.getOperacaoFinanceira());
            bv.setDataCompensacao(null);
            bv.setDataCancelamento(null);
            bv.setEstado(EstadoDeBaixa.EFETIVADO);
            new AtualizaDAO<>().atualiza(bv.construirComID());
        }
    }

    private void atualizaBaixasEmAberto(Cheque d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = new BaixaDAO().ePorCobranca(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            bv.setOperacaoFinanceira(d.getOperacaoFinanceira());
            bv.setDataCompensacao(null);
            bv.setDataCancelamento(null);
            bv.setEstado(EstadoDeBaixa.EM_DEFINICAO);
            new AtualizaDAO<>().atualiza(bv.construirComID());
        }
    }

    private void setOperacaoFinanceira() {
        if (e.getTipoLancamento() == TipoLancamento.EMITIDA) {
            e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
        } else {
            e.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
            e.setCompensacao(null);
        }
    }
    
    public void setDataCompensacaoNull(){
        if(e.getTipoLancamento() != TipoLancamento.EMITIDA){
            e.setCompensacao(null);
        }
    }

    public void setTipoEstadoCompensacao() {
        if (e.getCompensacao() != null) {
            e.setEstadoDeCheque(EstadoDeCheque.COMPENSADO);
        } else {
            e.setEstadoDeCheque(EstadoDeCheque.ABERTO);
        }
    }

    public void cancelarCompensar() {
        try {
            e.setCompensacao(null);
            e.setEstadoDeCheque(EstadoDeCheque.ABERTO);
            Cheque d = e.construirComID();
            new AtualizaDAO<>().atualiza(d);
            cancelaCompensacaoBaixas(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void cancelaCompensacaoBaixas(Cheque d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = new BaixaDAO().ePorCobranca(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            bv.setDataCompensacao(null);
            bv.setEstado(EstadoDeBaixa.EM_DEFINICAO);
            new AtualizaDAO<>().atualiza(bv.construirComID());
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = (Object) event.getObject();
        if (obj instanceof Cheque) {
            e = new ChequeBV((Cheque) obj);
        } else if (obj instanceof Banco) {
            e.setBanco((Banco) obj);
        } else if (obj instanceof NotaEmitida) {
            e.setVenda((NotaEmitida) obj);
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        }

    }

    public List<EstadoDeCheque> getEstadoDeCheque() {
        return Arrays.asList(EstadoDeCheque.values());
    }

    public List<OperacaoFinanceira> getOperacaoFinanceira() {
        return Arrays.asList(OperacaoFinanceira.values());
    }

    public List<TipoLancamento> getTipoLancemento() {
        return Arrays.asList(TipoLancamento.values());
    }

    public List<Cotacao> buscarListaDeCotacao() {
        listaCotacao = new CotacaoDAO().naEmissao(new Date()).listaDeResultados();
        return listaCotacao;
    }

    @Override
    public void limparJanela() {
        e = new ChequeBV();
        e.setTipoLancamento(TipoLancamento.EMITIDA);
        e.setCotacao(buscarListaDeCotacao().get(0));
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

    public List<Cotacao> getListaCotacao() {
        return listaCotacao;
    }

    public void setListaCotacao(List<Cotacao> listaCotacao) {
        this.listaCotacao = listaCotacao;
    }

}
