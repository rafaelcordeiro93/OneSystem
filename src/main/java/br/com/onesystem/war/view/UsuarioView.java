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
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
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

    public void add() {
        try {
            validaSenha(e.construir());
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
            Usuario usuarioExistente = e.construirComID();
            if (usuarioExistente.getId() != null) {
                if (!validaUsuarioExistente(usuarioExistente)) {
                    updateNoBanco(usuarioExistente);
                } else {

                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
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
        if (user.getSenha() != null) {
            e.setSenha(new MD5Util().md5Hex(e.getSenha()));
        } else if (!validaUsuarioExistente(user)) {
            Usuario buscar = new UsuarioDAO().buscarUsuarios().porId(user.getId()).resultado();
            e.setSenha(buscar.getSenha());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("informar_senha"));
        }
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
    public void buscaPorId() {

    }

    public void limparJanela() {
        e = new UsuarioBV();
    }

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

}
