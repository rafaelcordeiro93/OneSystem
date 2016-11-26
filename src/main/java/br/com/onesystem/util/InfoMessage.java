/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import javax.faces.application.FacesMessage;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author Rauber
 */
public final class InfoMessage {

    // Exibe a mensagem na janela!
    public static void print(String message) {
        getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", message));
    }

    // Exibe a mensagem no console
    public static void printConsole(String message) {
        System.out.println("Message: "
                + message);
    }
}
