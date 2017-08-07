package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Usuario;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoCorMenu;
import java.io.Serializable;

public class UsuarioBV implements Serializable, BuilderView<Usuario> {

    private Long id;
    private String senha;
    private Pessoa pessoa;
    private GrupoDePrivilegio grupoPrivilegio;
    private boolean supervisor;
    private String corTema;
    private String corLayout;
    private boolean overlayMenu;
    private TipoCorMenu corMenu;
    private boolean orientationRTL;

    public UsuarioBV(Usuario usuarioSelecionada) {
        this.id = usuarioSelecionada.getId();
        this.pessoa = usuarioSelecionada.getPessoa();
        this.grupoPrivilegio = usuarioSelecionada.getGrupoDePrivilegio();
        this.senha = usuarioSelecionada.getSenha();
        this.supervisor = usuarioSelecionada.isSupervisor();
        this.corTema = usuarioSelecionada.getCorTema();
        this.corLayout = usuarioSelecionada.getCorLayout();
        this.overlayMenu = usuarioSelecionada.isOverlayMenu();
        this.corMenu = usuarioSelecionada.getCorMenu();
        this.orientationRTL = usuarioSelecionada.isOrientationRTL();
    }

    public UsuarioBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public GrupoDePrivilegio getGrupoPrivilegio() {
        return grupoPrivilegio;
    }

    public void setGrupoPrivilegio(GrupoDePrivilegio grupoPrivilegio) {
        this.grupoPrivilegio = grupoPrivilegio;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }

    public String getCorTema() {
        return corTema;
    }

    public void setCorTema(String corTema) {
        this.corTema = corTema;
    }

    public String getCorLayout() {
        return corLayout;
    }

    public void setCorLayout(String corLayout) {
        this.corLayout = corLayout;
    }

    public boolean isOverlayMenu() {
        return overlayMenu;
    }

    public void setOverlayMenu(boolean overlayMenu) {
        this.overlayMenu = overlayMenu;
    }

    public TipoCorMenu getCorMenu() {
        return corMenu;
    }

    public void setCorMenu(TipoCorMenu corMenu) {
        this.corMenu = corMenu;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
    }

    public Usuario construir() throws DadoInvalidoException {
        return new Usuario(null, pessoa, senha, grupoPrivilegio, supervisor, corTema, corLayout, overlayMenu, corMenu, orientationRTL);
    }

    public Usuario construirComID() throws DadoInvalidoException {
        return new Usuario(id, pessoa, senha, grupoPrivilegio, supervisor, corTema, corLayout, overlayMenu, corMenu, orientationRTL);
    }
}
