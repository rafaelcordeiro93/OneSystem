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
import br.com.onesystem.dao.TransferenciaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.TransferenciaBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class ConsultaTransferenciaView extends BasicMBImpl<Transferencia, TransferenciaBV> implements Serializable {

    private TransferenciaBV transferenciaEstornada;
    private Cotacao cotacaoPadrao;
    private List<Cotacao> cotacaoBancariaLista;

    @Inject
    private ConfiguracaoService serviceConf;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void inicializar() {
        try {
            cotacaoBancariaLista = new CotacaoDAO().naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
            cotacaoPadrao = new CotacaoDAO().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().resultado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        e = new TransferenciaBV();
        transferenciaEstornada = new TransferenciaBV();
        cotacaoBancariaLista = new ArrayList<>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Transferencia) {
            e = new TransferenciaBV((Transferencia) event.getObject());
            inicializar();
        }
    }

    public void estorno() {
        try {
            transferenciaEstornada = new TransferenciaBV(e.construirComID());
            selecionaCotacao();
            transferenciaEstornada.setId(null);
            transferenciaEstornada.setBaixas(null);
            transferenciaEstornada.setEmissao(new Date());
            transferenciaEstornada.setTipoLancamentoBancario(TipoLancamentoBancario.ESTORNO);
            transferenciaEstornada.setEstornado(true);
            transferenciaEstornada.setIdRelacaoEstorno(e.getId());
            Transferencia d = transferenciaEstornada.construir();
            d.geraEstornoDaTransferenciaCom(transferenciaEstornada.getCotacaoDeOrigem(), transferenciaEstornada.getCotacaoDeDestino());//Cria os Estorno dos Valores Tranferidos entre as Contas.
            estornaTaxas(d);//Cria os Estorno das Taxas Bancarias que Haviam sido cobradas.
            new AdicionaDAO<>().adiciona(d);//Adiciona a Transferencia Estornada
            atualizaTransferencia(d);//Atualiza a Tranferencia Lançamento 
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void estornaTaxas(Transferencia d) {
        try {
            List<Baixa> baixas = new BaixaDAO().ePorTransferencia(e.construirComID()).eComDespesa().listaDeResultados();
            for (Baixa b : baixas) {
                BaixaBV bv = new BaixaBV(b);
                bv.setId(null);
                bv.setHistorico(null);
                bv.setTransferencia(null);
                bv.setEmissao(null);
                d.adiciona(bv.construir());
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionaCotacao() {
        if (transferenciaEstornada.getDestino() != null) {
            transferenciaEstornada.setCotacaoDeDestino(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(transferenciaEstornada.getDestino())).findFirst().get());
        } else {
            transferenciaEstornada.setCotacaoDeDestino(cotacaoPadrao);
        }
        if (transferenciaEstornada.getOrigem() != null) {
            transferenciaEstornada.setCotacaoDeOrigem(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(transferenciaEstornada.getOrigem())).findFirst().get());
        } else {
            transferenciaEstornada.setCotacaoDeOrigem(cotacaoPadrao);
        }
    }

    private void atualizaTransferencia(Transferencia d) {
        try {
            e.setEstornado(true);
            t = e.construirComID();
            t.setIdRelacaoEstorno(d);
            new AtualizaDAO<>().atualiza(t);
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
        TransferenciaBV de = new TransferenciaBV(new TransferenciaDAO().porId(t.getIdRelacaoEstorno()).resultado());
        de.setIdRelacaoEstorno(null);
        de.setEstornado(false);
        new AtualizaDAO<>().atualiza(de.construirComID());
    }

}
