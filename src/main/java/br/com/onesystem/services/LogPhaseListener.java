/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.DadosNecessarios;
import br.com.onesystem.war.builder.DadosNecessariosBV;
import br.com.onesystem.util.UsuarioLogadoUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rauber
 */
public class LogPhaseListener implements PhaseListener {

    @Inject
    private UsuarioDAO dao;

    @Inject
    private UsuarioLogadoUtil usuarioLogado;

    @Inject
    private DadosNecessarios dadosNecessarios;

    @Override
    public void afterPhase(PhaseEvent event) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext ec = context.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(true);
            String login = (String) session.getAttribute("minds.login.token");
            String janela = context.getViewRoot().getViewId();
            validaAcesso(login, janela, ec, session);
        } catch (IOException ex) {
            System.out.println("Falha ao redirecionar a página.");
        } catch (DadoInvalidoException ex) {
            Logger.getLogger(LogPhaseListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void validaAcesso(String login, String janela, ExternalContext ec, HttpSession session) throws IOException, DadoInvalidoException {
        if (login == null && !janela.equals("/login.xhtml")) {
            ec.redirect("/OneSystem-war/login.xhtml");
        } else if (login == null && janela.equals("/login.xhtml")) {
        } else if (login.equals("Anonymous") && !janela.equals("/OneSystem-war/configuracaoNecessaria.xhtml")) {//Adicionar funcionalidade de supervisor
            carregaDados(janela, session, ec);
            return;
        } else if (janela.contains("selecao") || janela.contains("dialogo")) {//Faz com que as janelas de Selecao nao precisem de permissoes
            return;
        } else if (janela.equals("/access.xhtml")) {

        } else if (!login.equals("Anonymous") && !janela.equals("/OneSystem-war/configuracaoNecessaria.xhtml")) { //Verifica a permissao nas janelas que forem abertas
            Usuario usuario = dao.porEmailString(login).resultado();
            boolean consulta = usuarioLogado.buscaPermissoesNoBanco(new BundleUtil().getLabel("Consultar"), janela, usuario);

            if (consulta) {
                usuarioLogado.carregaPreferenciasDo(usuario);
                carregaDados(janela, session, ec);
            } else {
                if (!janela.equals("/OneSystem-war/access.xhtml")) {
                    ec.redirect("/OneSystem-war/access.xhtml");
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    private void carregaDados(String janela, HttpSession session, ExternalContext ec) throws IOException {
        List<DadosNecessariosBV> pendencias = dadosNecessarios.valida(janela);

        Object object = session.getAttribute("onesystem.dadosNecessarios.list");
        if (!pendencias.isEmpty() && object == null) {
            session.setAttribute("onesystem.dadosNecessarios.list", pendencias);
            ec.redirect("/OneSystem-war/configuracaoNecessaria.xhtml");
        }
    }
}
