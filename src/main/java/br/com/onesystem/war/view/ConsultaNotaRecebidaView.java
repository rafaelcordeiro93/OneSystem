/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.war.builder.NotaRecebidaBV;
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
public class ConsultaNotaRecebidaView extends BasicMBImpl<NotaRecebida, NotaRecebidaBV> implements Serializable {

    @Inject
    private BundleUtil msg;

    private NotaRecebida notaRecebida;

    @PostConstruct
    public void construir() {
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof NotaRecebida) {
            notaRecebida = (NotaRecebida) obj;
        }
    }

    public NotaRecebida getNotaRecebida() {
        return notaRecebida;
    }

    public void setNotaRecebida(NotaRecebida notaRecebida) {
        this.notaRecebida = notaRecebida;
    }

    public String getZero() {
        if (notaRecebida != null) {
            return MoedaFomatter.format(notaRecebida.getMoedaPadrao(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    public String getOrigem(Cobranca parcela) {
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

    public void consultaParcela(Cobranca parcela) {
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
        notaRecebida = null;
    }
}
