/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped //javax.faces.view.ViewScoped;
public class UsuarioLogadoUtil implements Serializable {
    
    @Inject
    private UsuarioDAO usuarioDAO;

    public boolean getPrivilegio(String tipo) throws DadoInvalidoException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String login = (String) session.getAttribute("minds.login.token");
        if (login.equals("Anonymous")) {
            return true;
        }
        String janela = context.getViewRoot().getViewId();

        return buscaPermissoesNoBanco(tipo, janela, usuarioDAO.porEmailString(login).resultado());
    }

    public boolean getPrivilegio(String tipo, String j) throws DadoInvalidoException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String login = (String) session.getAttribute("minds.login.token");
        if (login.equals("Anonymous")) {
            return true;
        }
        String janela = j;

        return buscaPermissoesNoBanco(tipo, janela, usuarioDAO.porEmailString(login).resultado());
    }

    public boolean buscaPermissoesNoBanco(String tipo, String janela, Usuario usuario) throws DadoInvalidoException {
        List<Privilegio> listaPrivilegios = usuario.getGrupoDePrivilegio().getListaPrivilegios();
        if (usuario.isSupervisor() == true) {//se for supervisor não passa pelas regras 
            return true;
        }        
        if (tipo == new BundleUtil().getLabel("Consultar")) {
            return getPrivilegioConsultar(listaPrivilegios, janela);
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

    public void carregaPreferenciasDo(Usuario usuario) {
        PreferenciasDeUsuario gp = (PreferenciasDeUsuario) BeanUtil.getBeanNaSessao("preferenciasDeUsuario");

        gp.setLayout(usuario.getCorLayout());
        gp.setCorMenu(usuario.getCorMenu());
        gp.setTheme(usuario.getCorTema());
        gp.setOverlayMenu(usuario.isOverlayMenu());
        gp.setOrientationRTL(usuario.isOrientationRTL());
    }

    private boolean getPrivilegioConsultar(List<Privilegio> listaPrivilegios, String janela) throws DadoInvalidoException {
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

    public Usuario getUsuario() {
        return usuarioDAO.porEmailString(getEmailUsuario()).resultado();
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
