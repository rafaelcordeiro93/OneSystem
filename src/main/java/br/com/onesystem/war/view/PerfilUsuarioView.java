package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.PessoaFisica;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.UsuarioBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MD5Util;
import br.com.onesystem.valueobjects.TipoPessoa;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.service.UsuarioService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PerfilUsuarioView implements Serializable {

    private UsuarioBV usuario;
    private Usuario usuarioSelecionada;
    private PessoaBV pessoa;
    String senhaVelha = "";
    String senhaConfirma = "";

    @ManagedProperty("#{usuarioService}")
    private UsuarioService service;

    @PostConstruct
    public void init() {
        buscaUsuario();
    }

    public void update() throws IOException {
        try {
            atualizaPessoa();
            validaSenha();
            Usuario usuarioExistente = usuario.construirComID();
            if (usuarioExistente.getId() != null) {
                new AtualizaDAO<Pessoa>().atualiza(usuarioExistente.getPessoa());
                new AtualizaDAO<Usuario>().atualiza(usuarioExistente);
                InfoMessage.atualizado();
                buscaUsuario();
                InfoMessage.atualizado();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void atualizaPessoa() throws DadoInvalidoException {
        Pessoa p = new PessoaFisica(usuarioSelecionada.getPessoa().getDocumento(), usuarioSelecionada.getPessoa().getNascimento(), usuarioSelecionada.getPessoa().getId(),
                pessoa.getNome(), TipoPessoa.PESSOA_FISICA, usuarioSelecionada.getPessoa().getRuc(), usuarioSelecionada.getPessoa().isAtivo(), usuarioSelecionada.getPessoa().getDirecao(),
                usuarioSelecionada.getPessoa().getBairro(), usuarioSelecionada.getPessoa().isCategoriaCliente(), usuarioSelecionada.getPessoa().isCategoriaFornecedor(), usuarioSelecionada.getPessoa().isCategoriaVendedor(), usuarioSelecionada.getPessoa().isCategoriaTransportador(),
                usuarioSelecionada.getPessoa().getConjuge(), usuarioSelecionada.getPessoa().getDesconto(), usuarioSelecionada.getPessoa().getCadastro(), usuarioSelecionada.getPessoa().getObservacao(),
                usuarioSelecionada.getPessoa().getFiador(), usuarioSelecionada.getPessoa().getCidade(), pessoa.getTelefone(), pessoa.getEmail(), usuarioSelecionada.getPessoa().getContato());
        usuario.setPessoa(p);
    }

    private void validaSenha() throws EDadoInvalidoException, DadoInvalidoException {
        if (usuario.getSenha() != null) {
            if (!usuarioSelecionada.getSenha().equals(new MD5Util().md5Hex(senhaVelha))) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("senha_atual_errado"));
            } else if (!(new MD5Util().md5Hex(usuario.getSenha())).equals(new MD5Util().md5Hex(senhaConfirma))) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("senha_confirmacao_errado"));
            } else {
                usuario.setSenha(new MD5Util().md5Hex(usuario.getSenha()));
            }
        } else if (!validaUsuarioExistente(usuario.construirComID())) {
            usuario.setSenha(usuarioSelecionada.getSenha());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("informar_senha"));
        }
    }

    private void buscaUsuario() {
        limparJanela();
        usuarioSelecionada = service.buscarUsuarioPerfil();
        if (usuarioSelecionada != null) {
            usuario.setId(usuarioSelecionada.getId());
            usuario.setGrupoPrivilegio(usuarioSelecionada.getGrupoDePrivilegio());
            usuario.setCorTema(usuarioSelecionada.getCorTema());
            usuario.setCorLayout(usuarioSelecionada.getCorLayout());
            usuario.setOverlayMenu(usuarioSelecionada.isOverlayMenu());
            usuario.setOrientationRTL(usuarioSelecionada.isOrientationRTL());
            usuario.setDarkMenu(usuarioSelecionada.isDarkMenu());
            pessoa = new PessoaBV(usuarioSelecionada.getPessoa());

        }
    }

    private boolean validaUsuarioExistente(Usuario novoRegistro) {
        List<Usuario> lista = new UsuarioDAO().buscarUsuarios().porId(novoRegistro.getId()).listaDeResultados();
        return lista.isEmpty();
    }

    public void limparJanela() {
        usuario = new UsuarioBV();
        usuarioSelecionada = null;
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

    public String getSenhaVelha() {
        return senhaVelha;
    }

    public void setSenhaVelha(String senhaVelha) {
        this.senhaVelha = senhaVelha;
    }

    public String getSenhaConfirma() {
        return senhaConfirma;
    }

    public void setSenhaConfirma(String senhaConfirma) {
        this.senhaConfirma = senhaConfirma;
    }

    public PessoaBV getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaBV pessoa) {
        this.pessoa = pessoa;
    }

}
