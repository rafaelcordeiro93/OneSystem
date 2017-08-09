/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.dao.TransferenciaDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.TransferenciaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private List<Cotacao> cotacaoBancariaLista;
    private List<Cotacao> cotacaoEmpresaLista;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void inicializar() {
        cotacaoEmpresaLista = new CotacaoDAO().naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().listaDeResultados();
        cotacaoBancariaLista = new CotacaoDAO().naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
    }

    @Override
    public void limparJanela() {
        e = new TransferenciaBV();
        transferenciaEstornada = new TransferenciaBV();
        cotacaoEmpresaLista = new ArrayList<>();
        cotacaoBancariaLista = new ArrayList<>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new TransferenciaBV((Transferencia) event.getObject());
        inicializar();
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
            d.geraEstornoDaTransferenciaCom(transferenciaEstornada.getCotacaoDeOrigem(), transferenciaEstornada.getCotacaoDeDestino());
            new AdicionaDAO<>().adiciona(d);
            atualizaTransferencia(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaCotacao() {
        if (transferenciaEstornada.getDestino() != null) {
            transferenciaEstornada.setCotacaoDeDestino(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getDestino())).findFirst().get());
        }
        if (transferenciaEstornada.getOrigem() != null) {
            transferenciaEstornada.setCotacaoDeOrigem(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getOrigem())).findFirst().get());
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
            cancelaEstorno();
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
