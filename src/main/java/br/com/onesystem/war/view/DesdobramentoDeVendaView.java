/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Parcela;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.war.builder.NotaEmitidaBV;
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
public class DesdobramentoDeVendaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    @Inject
    private BundleUtil msg;

    private NotaEmitida notaEmitida;

    @PostConstruct
    public void construir() {
    }
    
    public void update() {
//        try {
//            Cidade cidadeExistente = e.construirComID();
//            if (cidadeExistente.getId() != null) {
//                if (validaCidadeExistente(cidadeExistente)) {
//                    updateNoBanco(cidadeExistente);
//                } else {
//                    throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_ja_existe"));
//                }
//            } else {
//                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
//            }
//        } catch (DadoInvalidoException die) {
//            die.print();
//        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof NotaEmitida) {
            notaEmitida = (NotaEmitida) obj;
        }
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public String getZero() {
        if (notaEmitida != null) {
            return MoedaFomatter.format(notaEmitida.getMoedaPadrao(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    public String getOrigem(Parcela parcela) {
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

    public void consultaParcela(Parcela parcela) {
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
        notaEmitida = null;
    }
}
