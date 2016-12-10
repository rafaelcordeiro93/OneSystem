/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Privilegio;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rauber
 */
public class LogPhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext ec = context.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(true);

            Object login = session.getAttribute("softone.login.token");
            String janela = context.getViewRoot().getViewId();
            validaAcesso(login, janela, ec);

        } catch (IOException ex) {
            System.out.println("Falha ao redirecionar a página.");
        }
    }

    private void validaAcesso(Object login, String janela, ExternalContext ec) throws IOException {
        if (login == null && !janela.equals("/login.xhtml")) {
            ec.redirect("/OneSystem-war/login.xhtml");
        } 
// else if (login == null && janela.equals("/login.xhtml")) {
//        } else if (janela.equals("/login.xhtml")) {
//            String email = login.toString();
//            Privilegio privilegio = new UsuarioDAO().getPrivilegio(email, janela);
//            boolean usuarioTemAcesso = new UsuarioDAO().getPrivilegio(email, "/home.xhtml").isConsultar();
//            if (usuarioTemAcesso) {
//                ec.redirect("/OneSystem-war/dashboard.xhtml");
//            }
//        } else if (janela.equals("/access-denied.xhtml")) {
//            } else {
//                String email = login.toString();
//                Privilegio privilegio = new UsuarioDAO().getPrivilegio(email, janela);
//                boolean usuarioTemAcesso = privilegio == null ? false : privilegio.isConsultar();
//                if (!usuarioTemAcesso) {
//                    ec.redirect("/OneSystem-war/access-denied.xhtml");
//                }
//            }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
