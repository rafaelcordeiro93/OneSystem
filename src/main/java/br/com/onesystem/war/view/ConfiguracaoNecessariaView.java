/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.war.builder.DadosNecessariosBV;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConfiguracaoNecessariaView implements Serializable {

    private List<DadosNecessariosBV> configuracoes;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);

        Object obj = session.getAttribute("onesystem.dadosNecessarios.list");
        if (obj != null) {
            configuracoes = (ArrayList<DadosNecessariosBV>) obj;
            session.removeAttribute("onesystem.dadosNecessarios.list");
        }
    }

    public List<DadosNecessariosBV> getConfiguracoes() {
        return configuracoes;
    }

    public void setConfiguracoes(List<DadosNecessariosBV> configuracoes) {
        this.configuracoes = configuracoes;
    }

}
