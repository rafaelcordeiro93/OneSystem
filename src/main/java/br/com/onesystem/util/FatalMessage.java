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
public final class FatalMessage {

    // Exibe a mensagem na janela!
    public static void print(String message, Throwable cause) {
        getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro Catastrófico!", message
                        + " - Causa : "
                        + cause));
    }
    // Exibe a mensagem no console

    public static void printConsole(String message, Throwable cause) {
        System.out.println("Message: "
                + message
                + " - Causa : "
                + cause);
    }
}
