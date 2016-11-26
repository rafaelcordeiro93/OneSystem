package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.UsuarioBV;
import br.com.onesystem.war.service.UsuarioService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MD5Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class UsuarioView implements Serializable {

    private boolean panel;
    private UsuarioBV usuario;
    private Usuario usuarioSelecionada;
    private List<Usuario> usuarioLista;
    private List<Usuario> usuariosFiltradas;
    private BundleUtil msg;

    @ManagedProperty("#{usuarioService}")
    private UsuarioService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        usuarioLista = service.buscarUsuarios();
        msg = new BundleUtil();
    }

    public void add() {
        try {
            if (usuario.getSenha() == null) {
                throw new EDadoInvalidoException("A senha deve ser informada.");
            }
            usuario.setSenha(new MD5Util().md5Hex(usuario.getSenha()));
            Usuario novoRegistro = usuario.construir();
            usuarioExiste(false);
            new AdicionaDAO<Usuario>().adiciona(novoRegistro);
            usuarioLista.add(novoRegistro);
            InfoMessage.print("¡Usuário '" + novoRegistro.getPessoa().getNome() + "' agregado con éxito!");
            limparJanela();
        }catch(ConstraintViolationException cve){
            String m = cve.getMessage();
            if(m.contains("pessoa_id")){
                String str = m.substring(m.indexOf("\'"), m.lastIndexOf("\'"));
                ErrorMessage.print("A pessoa selecionada já possui cadastro de usuário.");
            }
        }catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (usuario.getSenha() == null) {
                throw new EDadoInvalidoException("A senha deve ser informada.");
            }
            usuario.setSenha(new MD5Util().md5Hex(usuario.getSenha()));
            Usuario usuarioExistente = usuario.construirComID();
            if (usuarioExistente.getId() != null) {
                usuarioExiste(true);
                new AtualizaDAO<Usuario>(Usuario.class).atualiza(usuarioExistente);
                usuarioLista.set(usuarioLista.indexOf(usuarioExistente),
                        usuarioExistente);
                if (usuariosFiltradas != null && usuariosFiltradas.contains(usuarioExistente)) {
                    usuariosFiltradas.set(usuariosFiltradas.indexOf(usuarioExistente), usuarioExistente);
                }
                InfoMessage.print(msg.getMessage("Usuario") + " " + usuarioExistente.getPessoa().getNome() + " " + msg.getMessage("Alterado_com_sucesso"));
                limparJanela();
            } else {
                throw new EDadoInvalidoException(msg.getMessage("Usuario_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (usuarioLista != null && usuarioLista.contains(usuarioSelecionada)) {
                new RemoveDAO<Usuario>(Usuario.class).remove(usuarioSelecionada, usuarioSelecionada.getId());
                usuarioLista.remove(usuarioSelecionada);
                if (usuariosFiltradas != null && usuariosFiltradas.contains(usuarioSelecionada)) {
                    usuariosFiltradas.remove(usuarioSelecionada);
                }
                InfoMessage.print(msg.getMessage("Usuario") + " " + this.usuario.getPessoa().getNome() + " " + msg.getMessage("Removido_com_sucesso"));
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void usuarioExiste(Boolean pessoaExiste) throws DadoInvalidoException {
        String email = usuario.getEmail();
        try {
            if (email != null && !email.trim().equals("")) {
                if (pessoaExiste) {
                    for (Usuario usuarioDaLista : usuarioLista) {
                        if (usuarioDaLista.getEmail().equals(email)
                                && usuarioDaLista.getId() != usuario.getId()) {
                            throw new IDadoInvalidoException(msg.getMessage("Usuario_Ja_Existe"));
                        }
                    }
                } else {
                    for (Usuario usuarioDaLista : usuarioLista) {
                        if (usuarioDaLista.getEmail().equals(email)) {
                            throw new IDadoInvalidoException(msg.getMessage("Usuario_Ja_Existe"));
                        }
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    public void selecionaUsuario(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        usuario.setPessoa(pessoa);
    }

    public void selecionaGrupoPrivilegio(SelectEvent event) {
        GrupoDePrivilegio grupoDePrivilegio = (GrupoDePrivilegio) event.getObject();
        usuario.setGrupoPrivilegio(grupoDePrivilegio);
    }

    public void limparJanela() {
        usuario = new UsuarioBV();
        usuarioSelecionada = new Usuario();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        usuario = new UsuarioBV(usuarioSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
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

    public List<Usuario> getUsuarioLista() {
        return usuarioLista;
    }

    public void setUsuarioLista(List<Usuario> usuarioLista) {
        this.usuarioLista = usuarioLista;
    }

    public List<Usuario> getUsuariosFiltradas() {
        return usuariosFiltradas;
    }

    public void setUsuariosFiltradas(List<Usuario> usuariosFiltradas) {
        this.usuariosFiltradas = usuariosFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

}
