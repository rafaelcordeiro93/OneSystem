/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.MD5Util;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rauber
 */
@ManagedBean
@ViewScoped
public class LoginView implements Serializable {

    private String login;
    private String senha;
    private List<Usuario> listaDeUsuarios;
    private String mensagem = null;

    public LoginView() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("minds.login.token");
        session.removeAttribute("minds.nome.token");
    }

    public String logar() {
        if (!listaDeUsuarios.isEmpty()) {
            mensagem = null;
            MD5Util criptografia = new MD5Util();
            for (Usuario usuarioCadastrado : listaDeUsuarios) {
                if (login != null && login.equals(usuarioCadastrado.getPessoa().getEmail())
                        && senha != null && criptografia.md5Hex(senha).equals(usuarioCadastrado.getSenha())) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                    session.setAttribute("minds.login.token", login);
                    session.setAttribute("minds.nome.token", usuarioCadastrado.getPessoa().getNome());
                    session.setAttribute("minds.GrupoPV.token", usuarioCadastrado.getGrupoDePrivilegio().getNome());
                    return "dashboard?faces-redirect=true";
                }
            }
            mensagem = "Usuário ou senha inválidos!";
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("minds.login.token", "Anonymous");
        session.setAttribute("minds.nome.token", "Anonymous");
        return "dashboard?faces-redirect=true";
    }  

    @PostConstruct
    public void construct() {
        listaDeUsuarios = new ArmazemDeRegistros<Usuario>(Usuario.class
        ).listaTodosOsRegistros();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);
        String username = (String) session.getAttribute("minds.nome.token");
        return username;
    }

    public String getminhaSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        System.out.println("" + session.getValue("minds.nome.token").toString());
        return session.getValue("minds.nome.token").toString();
    }
}
