/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.SaqueBancarioBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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
        try{
        e = new SaqueBancarioBV();
        e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
        }catch(DadoInvalidoException die){
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof SaqueBancario) {
            e = new SaqueBancarioBV((SaqueBancario) event.getObject());
        }
    }
}
