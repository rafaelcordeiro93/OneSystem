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
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PerfilUsuarioView implements Serializable {

    private UsuarioBV usuario;
    private Usuario usuarioSelecionada;
    private PessoaBV pessoa;
    String senhaVelha = "";
    String senhaConfirma = "";
    private Map<String, String> themeColors;
    private String theme = "blue";
    private String layout = "pink";
    private boolean overlayMenu;
    private boolean darkMenu;
    private boolean orientationRTL;

    @ManagedProperty("#{usuarioService}")
    private UsuarioService service;

    @PostConstruct
    public void init() {
        themeColors = new HashMap<String, String>();
        themeColors.put("turquoise", "#47c5d4");
        themeColors.put("blue", "#3192e1");
        themeColors.put("orange", "#ff9c59");
        themeColors.put("purple", "#985edb");
        themeColors.put("pink", "#e42a7b");
        themeColors.put("purple", "#985edb");
        themeColors.put("green", "#5ea980");
        themeColors.put("black", "#545b61");
        buscaUsuario();
    }

    public void update() {
        try {
            atualizaPessoa();
            validaSenha(usuario.construirComID());
            System.out.println(usuario.getPessoa());
            Usuario usuarioExistente = usuario.construirComID();
            
            if (usuarioExistente.getId() != null) {
                new AtualizaDAO<Usuario>(Usuario.class).atualiza(usuarioExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void atualizaPessoa() throws DadoInvalidoException {
        Pessoa ps = new PessoaFisica(usuarioSelecionada.getPessoa().getDocumento(), usuarioSelecionada.getPessoa().getNascimento(), usuarioSelecionada.getPessoa().getId(),
                pessoa.getNome(), TipoPessoa.PESSOA_FISICA, usuarioSelecionada.getPessoa().getRuc(), usuarioSelecionada.getPessoa().isAtivo(), usuarioSelecionada.getPessoa().getDirecao(),
                usuarioSelecionada.getPessoa().getBairro(), usuarioSelecionada.getPessoa().isCategoriaCliente(), usuarioSelecionada.getPessoa().isCategoriaFornecedor(), usuarioSelecionada.getPessoa().isCategoriaVendedor(), usuarioSelecionada.getPessoa().isCategoriaTransportador(),
                usuarioSelecionada.getPessoa().getConjuge(), usuarioSelecionada.getPessoa().getDesconto(), usuarioSelecionada.getPessoa().getCadastro(), usuarioSelecionada.getPessoa().getObservacao(),
                usuarioSelecionada.getPessoa().getFiador(), usuarioSelecionada.getPessoa().getCidade(), pessoa.getTelefone(), pessoa.getEmail(), usuarioSelecionada.getPessoa().getContato());

        usuario.setPessoa(ps);
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

    private void buscaUsuario() {
        usuario = new UsuarioBV();
        usuarioSelecionada = null;
        usuarioSelecionada = service.buscarUsuarioPerfil();
        if (usuarioSelecionada != null) {
            usuario.setId(usuarioSelecionada.getId());
            usuario.setGrupoPrivilegio(usuarioSelecionada.getGrupoDePrivilegio());
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isDarkMenu() {
        return this.darkMenu;
    }

    public void setDarkMenu(boolean value) {
        this.darkMenu = value;
    }

    public boolean isOverlayMenu() {
        return this.overlayMenu;
    }

    public void setOverlayMenu(boolean value) {
        this.overlayMenu = value;
    }

    public Map getThemeColors() {
        return this.themeColors;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
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
