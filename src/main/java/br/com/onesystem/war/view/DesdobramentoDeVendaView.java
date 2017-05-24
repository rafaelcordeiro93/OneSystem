/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.builder.CreditoBV;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DesdobramentoDeVendaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    private NotaEmitida nota;

    @PostConstruct
    public void init() {
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof NotaEmitida && "consultaDesdNotaEmitida-search".equals(idComponent)) {
            this.nota = (NotaEmitida) obj;
        }
    }

    public void limparJanela() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public NotaEmitida getNota() {
        return nota;
    }

    public void setNota(NotaEmitida nota) {
        this.nota = nota;
    }

}
