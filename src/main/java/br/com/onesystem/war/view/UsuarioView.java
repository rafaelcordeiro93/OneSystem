package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class UsuarioView extends BasicMBImpl<Usuario> implements Serializable {

    private UsuarioBV usuario;
    private Usuario usuarioSelecionada;

    @ManagedProperty("#{usuarioService}")
    private UsuarioService service;

    @PostConstruct
    public void init() {
        limparJanela();

    }

    public void add() {
        try {
            validaSenha(usuario.construir());
            Usuario novoRegistro = usuario.construir();
            if (validaPessoaExistente(novoRegistro)) {
                new AdicionaDAO<Usuario>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("usuario_ja_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            validaSenha(usuario.construirComID());
            Usuario usuarioExistente = usuario.construirComID();
            if (usuarioExistente.getId() != null) {
                if (!validaUsuarioExistente(usuarioExistente)) {
                    new AtualizaDAO<Usuario>(Usuario.class).atualiza(usuarioExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {

                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (usuarioSelecionada != null) {
                new RemoveDAO<Usuario>(Usuario.class).remove(usuarioSelecionada, usuarioSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
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
            usuario.setSenha(new MD5Util().md5Hex(usuario.getSenha()));
        } else if (!validaUsuarioExistente(user)) {
            Usuario buscar = new UsuarioDAO().buscarUsuarios().porId(user.getId()).resultado();
            usuario.setSenha(buscar.getSenha());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("informar_senha"));
        }
    }

    @Override
    public void selecionar(SelectEvent e) {
        Object obj = e.getObject();

        if (obj instanceof Usuario) {
            Usuario u = (Usuario) obj;
            usuario = new UsuarioBV(u);
            usuarioSelecionada = u;

        } else if (obj instanceof Pessoa) {
            Pessoa pessoa = (Pessoa) obj;
            usuario.setPessoa(pessoa);
        } else if (obj instanceof GrupoDePrivilegio) {
            GrupoDePrivilegio grupo = (GrupoDePrivilegio) obj;
            usuario.setGrupoPrivilegio(grupo);
        }
    }

    @Override
    public void buscaPorId() {

    }

    public void limparJanela() {
        usuario = new UsuarioBV();
        usuarioSelecionada = new Usuario();
    }

    public void desfazer() {
        if (usuarioSelecionada != null) {
            usuario = new UsuarioBV(usuarioSelecionada);
        }
    }

    public UsuarioBV getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBV usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioSelecionada() {
        return usuarioSelecionada;
    }

    public void setUsuarioSelecionada(Usuario usuarioSelecionada) {
        this.usuarioSelecionada = usuarioSelecionada;
    }

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

}
