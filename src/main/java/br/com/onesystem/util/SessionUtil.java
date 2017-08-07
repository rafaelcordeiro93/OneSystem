/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.exception.impl.FDadoInvalidoException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class SessionUtil {

    public static void put(Object objeto, String key, FacesContext contexto) throws FDadoInvalidoException {
        if (key != null && key.trim().length() >= 3 && objeto != null && contexto != null) {
            HttpSession session = (HttpSession) contexto.getExternalContext().getSession(true);
            session.removeAttribute("minds." + key + ".key");
            session.setAttribute("minds." + key + ".key", objeto);
        } else {
            throw new FDadoInvalidoException("{SessaoUtil - put} Todos os campos devem ser informados.");
        }
    }

    public static Object getObject(String key, FacesContext contexto) throws FDadoInvalidoException {
        if (key != null && key.trim().length() >= 3 && contexto != null) {
            HttpSession session = (HttpSession) contexto.getExternalContext().getSession(true);
            return session.getAttribute("minds." + key + ".key");
        } else {
            throw new FDadoInvalidoException("{SessaoUtil - getObject} Todos os campos devem ser informados.");
        }
    }

    public static void remove(String key, FacesContext contexto) throws FDadoInvalidoException {
        if (key != null && key.trim().length() >= 3 && contexto != null) {
            HttpSession session = (HttpSession) contexto.getExternalContext().getSession(true);
            session.removeAttribute("minds." + key + ".key");
        } else {
            throw new FDadoInvalidoException("{SessaoUtil - remove} Todos os campos devem ser informados.");
        }
    }

}
