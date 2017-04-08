/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.util;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class UsuarioLogadoUtil {

    public boolean getPrivilegio(String tipo) throws DadoInvalidoException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String login = (String) session.getAttribute("minds.login.token");
        String janela = context.getViewRoot().getViewId();
       
        Usuario usuario = new UsuarioDAO().buscarUsuarios().porEmailString(login).resultado();
        List<Privilegio> listaPrivilegios = usuario.getGrupoDePrivilegio().getListaPrivilegios();

        if (tipo == new BundleUtil().getLabel("Consultar")) {
            return getPrivilegioCulsultar(listaPrivilegios, janela);
        }
        if (tipo == new BundleUtil().getLabel("Adicionar")) {
            return getPrivilegioAdicionar(listaPrivilegios, janela);
        }
        if (tipo == new BundleUtil().getLabel("Atualizar")) {
            return getPrivilegioAlterar(listaPrivilegios, janela);
        }
        if (tipo == new BundleUtil().getLabel("Remover")) {
            return getPrivilegioRemover(listaPrivilegios, janela);
        } else {
            return false;
        }
    }

    private boolean getPrivilegioCulsultar(List<Privilegio> listaPrivilegios, String janela) throws DadoInvalidoException {
        for (Privilegio privilegioEncontrado : listaPrivilegios) {
            if (privilegioEncontrado.getJanela().getEndereco().equals(janela)) {
                if (privilegioEncontrado.isConsultar() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getPrivilegioAlterar(List<Privilegio> listaPrivilegios, String janela) throws DadoInvalidoException {
        for (Privilegio privilegioEncontrado : listaPrivilegios) {
            if (privilegioEncontrado.getJanela().getEndereco().equals(janela)) {
                if (privilegioEncontrado.isAlterar() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getPrivilegioAdicionar(List<Privilegio> listaPrivilegios, String janela) throws DadoInvalidoException {
        for (Privilegio privilegioEncontrado : listaPrivilegios) {
            if (privilegioEncontrado.getJanela().getEndereco().equals(janela)) {
                if (privilegioEncontrado.isAdicionar() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getPrivilegioRemover(List<Privilegio> listaPrivilegios, String janela) throws DadoInvalidoException {
        for (Privilegio privilegioEncontrado : listaPrivilegios) {
            if (privilegioEncontrado.getJanela().getEndereco().equals(janela)) {
                if (privilegioEncontrado.isRemover() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getNomeUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String nome = (String) session.getAttribute("minds.nome.token");
        return nome;
    }

    public String getEmailUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String email = (String) session.getAttribute("minds.login.token");
        return email;
    }

    public String getGrupoPrivilegio() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String grupo = (String) session.getAttribute("minds.GrupoPV.token");
        return grupo;

    }
}
