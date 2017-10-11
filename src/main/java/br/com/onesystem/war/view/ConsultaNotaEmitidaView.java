/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeLayout;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaNotaEmitidaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    @Inject
    private BundleUtil msg;

    @Inject
    private LayoutDeImpressaoService service;
    
    @PostConstruct
    public void construir() {
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof NotaEmitida) {
            t = (NotaEmitida) obj;
        }
    }
    
    public void imprimir() {
        try {
            if (t != null) {
                LayoutDeImpressao layout = service.getLayoutPorTipoDeLayout(TipoLayout.NOTA_EMITIDA);
                new ImpressoraDeLayout(t.getItens(), layout).addParametro("notaEmitida", t).visualizarPDF();
                t = null; // libera memoria do objeto impresso.
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Selecione_um_registro"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public String getZero() {
        if (t != null) {
            return MoedaFormatter.format(t.getCotacao().getConta().getMoeda(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }
    
     public void cancela() {
        try {
            t.cancela();
            new AtualizaDAO<>().atualiza(t);
            InfoMessage.atualizado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public String getOrigem(CobrancaVariavel parcela) {
        if (parcela instanceof Cheque) {
            return msg.getLabel("Cheque");
        } else if (parcela instanceof BoletoDeCartao) {
            return msg.getLabel("Cartao");
        } else if (parcela instanceof Titulo) {
            return msg.getLabel("Titulo");
        } else {
            return msg.getLabel("Nao_Encontrado");
        }
    }

    public void consultaParcela(CobrancaVariavel parcela) {
        if (parcela instanceof Cheque) {
            InfoMessage.print(msg.getLabel("Cheque"));
        } else if (parcela instanceof BoletoDeCartao) {
            InfoMessage.print(msg.getLabel("Cartao"));
        } else if (parcela instanceof Titulo) {
            InfoMessage.print(msg.getLabel("Titulo"));
        } else {
            InfoMessage.print(msg.getLabel("Nao_Encontrado"));
        }
    }

    @Override
    public void limparJanela() {
        t = null;
    }
}
