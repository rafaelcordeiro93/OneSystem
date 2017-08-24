/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.war.builder.SaqueBancarioBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaSaqueBancarioView extends BasicMBImpl<SaqueBancario, SaqueBancarioBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void limparJanela() {
        e = new SaqueBancarioBV();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof SaqueBancario) {
            e = new SaqueBancarioBV((SaqueBancario) event.getObject());
        }
    }
}
