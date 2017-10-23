/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.exception.DadoInvalidoException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael
 */
@Named
@ViewScoped
public class PageRedirectUtil implements Serializable {

    /**
     * Redireciona a página
     *
     * @param pagina Página para ser redirecionada
     */
    public void redirecionarPagina(String pagina) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            String url = context.getExternalContext().getRequestContextPath();

            context.getExternalContext().redirect(url + pagina + ".xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PageRedirectUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
