/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Comanda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.war.builder.ComandaBV;
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
public class ConsultaComandaView extends BasicMBImpl<Comanda, ComandaBV> implements Serializable {

    @PostConstruct
    public void construir() {
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Comanda) {
            t = (Comanda) obj;
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
            updateNoBanco(t);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void limparJanela() {
        t = null;
    }

}
