/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaNotaEmitidaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    private NotaEmitida notaEmitida;

    @PostConstruct
    public void construir() {
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

    @Override
    public void limparJanela() {
        notaEmitida = null;
    }
}
