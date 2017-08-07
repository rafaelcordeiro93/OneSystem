package br.com.onesystem.war.view;

import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.builder.UsuarioBV;
import br.com.onesystem.war.service.UsuarioService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MD5Util;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoCorMenu;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class UsuarioView extends BasicMBImpl<Usuario, UsuarioBV> implements Serializable {

    @Inject
    private UsuarioService service;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Usuario) {
            e = new UsuarioBV((Usuario) obj);
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof GrupoDePrivilegio) {
            e.setGrupoPrivilegio((GrupoDePrivilegio) obj);
        }
    }

    @Override
    public void limparJanela() {
        e = new UsuarioBV();
    }

    public void add() {
        try {
            validaSenha(e.construir());
            adicionaOpcoesDeLayout();
            Usuario novoRegistro = e.construir();
            if (validaPessoaExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("usuario_ja_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            validaSenha(e.construirComID());
            adicionaOpcoesDeLayout();
            Usuario usuarioExistente = e.construirComID();
            if (usuarioExistente.getId() != null) {
                updateNoBanco(usuarioExistente);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void deletsae() {
        if (e != null && e.getId() != null) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext ec = context.getExternalContext();
                ec.redirect("/OneSystem-war/login.xhtml");
                deleteNoBanco((Usuario) e.construirComID(), e.getId());
            } catch (DadoInvalidoException ex) {
                ex.print();
            } catch (IOException ex) {
            }
        }
    }

    private boolean validaPessoaExistente(Usuario novoRegistro) {
        List<Usuario> lista = new UsuarioDAO().buscarUsuarios().porPessoa(novoRegistro.getPessoa()).listaDeResultados();
        return lista.isEmpty();
    }

    private boolean validaUsuarioExistente(Usuario novoRegistro) {
        List<Usuario> lista = new UsuarioDAO().buscarUsuarios().porId(novoRegistro.getId()).listaDeResultados();
        return lista.isEmpty();
    }

    private void validaSenha(Usuario user) throws EDadoInvalidoException, DadoInvalidoException {
        if (user.getSenha() != null && user.getSenha().length() >= 4) {
            e.setSenha(new MD5Util().md5Hex(e.getSenha()));
        } else if (!validaUsuarioExistente(user) && user.getSenha().length() >= 4) { //busca a senha q ja estava no cadastro se não houve alteração.
            Usuario buscar = new UsuarioDAO().buscarUsuarios().porId(user.getId()).resultado();
            e.setSenha(buscar.getSenha());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("informar_senha"));
        }
    }

    private void adicionaOpcoesDeLayout() {
        e.setCorLayout(e.getCorLayout() == null ? "blue" : e.getCorLayout());
        e.setCorMenu(e.getCorMenu() == null ? TipoCorMenu.CINZA : e.getCorMenu());
        e.setCorTema(e.getCorTema() == null ? "blue" : e.getCorTema());
        e.setOverlayMenu(true);
        e.setOrientationRTL(false);
    }

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }
}
