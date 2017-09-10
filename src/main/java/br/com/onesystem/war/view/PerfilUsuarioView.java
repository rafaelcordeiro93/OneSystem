package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.builder.UsuarioBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MD5Util;
import br.com.onesystem.valueobjects.TipoCorMenu;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.service.UsuarioService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PerfilUsuarioView extends BasicMBImpl<Usuario, UsuarioBV> implements Serializable {

    private PessoaBV pessoa;
    String senhaVelha = "";
    String senhaConfirma = "";

    @Inject
    private UsuarioService service;

    @Inject
    private AtualizaDAO<Pessoa> atualizaDAOPessoa;
    
    @Inject
    private PessoaDAO pessoaDAO;
    
    @Inject
    private UsuarioDAO usuarioDAO;
    
    @PostConstruct
    public void init() {
        buscaUsuario();
    }

    public void update() {
        try {
            validaSenha();
            t = e.construirComID();
            if (t.getId() != null) {
                atualizaDAOPessoa.atualiza(pessoa.construirComID());
                atualizaDAO.atualiza(t);
                refresh();
                InfoMessage.atualizado();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void validaSenha() throws EDadoInvalidoException, DadoInvalidoException {
        if (e.getSenha() != null) {
            if (!t.getSenha().equals(new MD5Util().md5Hex(senhaVelha))) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("senha_atual_errado"));
            } else if (!(new MD5Util().md5Hex(e.getSenha())).equals(new MD5Util().md5Hex(senhaConfirma))) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("senha_confirmacao_errado"));
            } else {
                e.setSenha(new MD5Util().md5Hex(e.getSenha()));
            }
        } else if (!validaUsuarioExistente(e.construirComID())) {
            e.setSenha(t.getSenha());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("informar_senha"));
        }
    }

    private void buscaUsuario() {
        limparJanela();
        t = service.buscarUsuarioPerfil();
        if (t != null) {
            e = new UsuarioBV(t);
            pessoa = new PessoaBV(pessoaDAO.porId(e.getId()).resultado());
        }
    }

    public void refresh() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("perfilUsuario.xhtml");//atualiza a pagina para atualizar a aparencia do sistema
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public String getMascaraDeTelefone() {
        return new BundleUtil().getMessage("mascara_de_telefone");
    }

    public List<TipoCorMenu> getCoresDeMenu() {
        return Arrays.asList(TipoCorMenu.values());
    }

    private boolean validaUsuarioExistente(Usuario novoRegistro) {
        List<Usuario> lista = usuarioDAO.porId(novoRegistro.getId()).listaDeResultados();
        return lista.isEmpty();
    }

    public void limparJanela() {
        e = new UsuarioBV();
        t = null;
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

    public UsuarioService getService() {
        return service;
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

    public PessoaBV getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaBV pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public void selecionar(SelectEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
