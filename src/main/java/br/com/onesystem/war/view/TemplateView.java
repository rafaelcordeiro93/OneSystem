/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rafael 
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class TemplateView implements Serializable {
    
    private String nome;
    private String janela;
     
    public TemplateView() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        
        String str = context.getViewRoot().getViewId().replaceAll("/", "").replaceAll(".xhtml", "");
        janela = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        nome = session.getAttribute("softone.nome.token").toString();
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getJanela() {
        return janela;
    }
    
    public void setJanela(String janela) {
        this.janela = janela;
    }
    
}
