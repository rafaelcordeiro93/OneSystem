/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.dao.DepositoBancarioDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.DepositoBancarioBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaDepositoBancarioView extends BasicMBImpl<DepositoBancario, DepositoBancarioBV> implements Serializable {

    private DepositoBancarioBV depositoEstonado;
    private List<Cotacao> cotacaoBancariaLista;
    private List<Cotacao> cotacaoEmpresaLista;

    @Inject
    private AdicionaDAO<DepositoBancario> adicionaDAO;
    @Inject
    private AtualizaDAO<DepositoBancario> atualizaDAO;
    @Inject
    private AtualizaDAO<Baixa> atualizaBaixaDAO;
    @Inject
    private BaixaDAO baixaDAO;
    @Inject
    private CotacaoDAO cotacaoDAO;
    @Inject
    private DepositoBancarioDAO depositoBancarioDAO;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void inicializar() {
        cotacaoEmpresaLista = cotacaoDAO.naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().listaDeResultados();
        cotacaoBancariaLista = cotacaoDAO.naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
    }

    @Override
    public void limparJanela() {
        try {
            e = new DepositoBancarioBV();
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            depositoEstonado = new DepositoBancarioBV();
            cotacaoEmpresaLista = new ArrayList<>();
            cotacaoBancariaLista = new ArrayList<>();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof DepositoBancario) {
            e = new DepositoBancarioBV((DepositoBancario) event.getObject());
            inicializar();
        }
    }

    public void estorno() {
        try {
            depositoEstonado = new DepositoBancarioBV(e.construirComID());
            selecionaCotacao();
            depositoEstonado.setId(null);
            depositoEstonado.setBaixas(null);
            depositoEstonado.setCheques(null);
            depositoEstonado.setEmissao(new Date());
            depositoEstonado.setTipoLancamentoBancario(TipoLancamentoBancario.ESTORNO);
            depositoEstonado.setEstornado(true);
            depositoEstonado.setIdRelacaoEstorno(e.getId());
            DepositoBancario d = depositoEstonado.construir();
            d.geraEstornoDoDepositoCom(depositoEstonado.getCotacaoDeOrigem(), depositoEstonado.getCotacaoDeDestino());
            adicionaDAO.adiciona(d);
            atualizaDeposito(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void compensar() {
        try {
            DepositoBancario d = e.construirComID();
            if (d.getCompensacao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Data_Compensacao_Deve_Ser_Informada"));
            }
            atualizaDAO.atualiza(d);
            atualizaBaixas(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void cancelarCompensar() {
        try {
            e.setCompensacao(null);
            DepositoBancario d = e.construirComID();
            atualizaDAO.atualiza(d);
            cancelaCompensacaoBaixas(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaCotacao() {
        if (depositoEstonado.getDestino() != null) {
            depositoEstonado.setCotacaoDeDestino(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getDestino())).findFirst().get());
        }
        if (depositoEstonado.getOrigem() != null) {
            depositoEstonado.setCotacaoDeOrigem(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getOrigem())).findFirst().get());
        }
    }

    private void atualizaDeposito(DepositoBancario d) {
        try {
            e.setEstornado(true);
            e.setCompensacao(null);
            t = e.construirComID();
            t.setIdRelacaoEstorno(d);
            atualizaDAO.atualiza(t);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void delete() {
        try {
            t = e.construirComID();
            if (t.getTipoLancamentoBancario().equals(TipoLancamentoBancario.ESTORNO)) {
                cancelaEstorno();
            }
            deleteNoBanco(t, t.getId());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void cancelaEstorno() throws ConstraintViolationException, DadoInvalidoException {
        DepositoBancarioBV de = new DepositoBancarioBV(depositoBancarioDAO.porId(t.getIdRelacaoEstorno()).resultado());
        de.setIdRelacaoEstorno(null);
        de.setEstornado(false);
        atualizaDAO.atualiza(de.construirComID());
    }

    private void atualizaBaixas(DepositoBancario d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = baixaDAO.ePorDepositoBancario(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            if (b.getOperacaoFinanceira().equals(OperacaoFinanceira.ENTRADA)) {
                bv.setDataCompensacao(d.getCompensacao());
            }
            bv.setEstado(EstadoDeBaixa.EFETIVADO);
            atualizaBaixaDAO.atualiza(bv.construirComID());
        }
    }

    private void cancelaCompensacaoBaixas(DepositoBancario d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = baixaDAO.ePorDepositoBancario(d).listaDeResultados();
        for (Baixa b : listaBaixa) {
            BaixaBV bv = new BaixaBV(b);
            if (b.getOperacaoFinanceira().equals(OperacaoFinanceira.ENTRADA)) {
                bv.setDataCompensacao(null);
            }
            bv.setEstado(EstadoDeBaixa.EM_DEFINICAO);
            atualizaBaixaDAO.atualiza(bv.construirComID());
        }
    }

}
