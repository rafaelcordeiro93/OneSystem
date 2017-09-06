/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MD5Util;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.service.CaixaService;
import br.com.onesystem.war.service.FilialService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class LoginView implements Serializable {

    private String login;
    private String senha;
    private List<Usuario> listaDeUsuarios;
    private String mensagem = null;

    @Inject
    private CaixaService service;

    @Inject
    private FilialService filialService;

    @Inject
    private ArmazemDeRegistros<Usuario> armazem;

    public String logar() {
        try {
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

                        Caixa caixa = service.getCaixaAbertoDo(usuarioCadastrado);
                        if (service.getCaixaAbertoDo(usuarioCadastrado) != null) {
                            SessionUtil.put(caixa, "caixa", FacesContext.getCurrentInstance());
                        }

                        List<Filial> filiais = filialService.buscarFiliais();
                        if (filiais.size() == 1) {
                            SessionUtil.put(filiais.get(0), "filial", FacesContext.getCurrentInstance());
                        }
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
        } catch (DadoInvalidoException die) {
            die.print();
            return null;
        }
    }

    @PostConstruct
    public void construct() {
        listaDeUsuarios = armazem.daClasse(Usuario.class).listaTodosOsRegistros();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("minds.login.token");
        session.removeAttribute("minds.nome.token");
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
        return session.getValue("minds.nome.token").toString();
    }
}
