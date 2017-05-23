/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoCorMenu;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rauber
 */
@Entity
@SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO",
        initialValue = 1, allocationSize = 1)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @OneToOne(optional = false)
    private Pessoa pessoa;
    @NotNull(message = "{senha_not_null}")
    @Length(min = 8, max = 100, message = "{senha_length}")
    @Column(nullable = false)
    private String senha;
    @NotNull(message = "{grupo_privilegio_not_null}")
    @ManyToOne(optional = false)
    private GrupoDePrivilegio grupoPrivilegio;
    @OneToMany(mappedBy = "usuario")
    private List<HistoricoDeOrcamento> historicoOrcamentos;
    private boolean supervisor = false;
    private String corTema;
    private String corLayout;
    private boolean overlayMenu;
    @Enumerated(EnumType.STRING)
    private TipoCorMenu corMenu;
    private boolean orientationRTL;
    @OneToMany(mappedBy = "usuario")
    private List<Caixa> listaCaixa;

    public Usuario() {
    }

    public Usuario(Long id, Pessoa pessoa, String senha, GrupoDePrivilegio grupoPrivilegio,
            boolean supervisor, String corTema, String corLayout, boolean overlayMenu, TipoCorMenu corMenu, boolean orientationRTL) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.senha = senha;
        this.grupoPrivilegio = grupoPrivilegio;
        this.supervisor = supervisor;
        this.corTema = corTema;
        this.corLayout = corLayout;
        this.orientationRTL = orientationRTL;
        this.overlayMenu = overlayMenu;
        this.corMenu = corMenu;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getSenha() {
        return senha;
    }

    public GrupoDePrivilegio getGrupoDePrivilegio() {
        return grupoPrivilegio;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public String getCorTema() {
        return corTema;
    }

    public String getCorLayout() {
        return corLayout;
    }

    public TipoCorMenu getCorMenu() {
        return corMenu;
    }

    public boolean isOverlayMenu() {
        return overlayMenu;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Usuario)) {
            return false;
        }
        Usuario outro = (Usuario) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "grupoPrivilegio");
        new ValidadorDeCampos<Usuario>().valida(this, campos);
    }

    @Override
    public String toString() {
        return "Usuário - " + "id: " + id + " - pessoa: " + pessoa.getId() + " - senha: " + senha
                + " - grupo de privilégio: " + grupoPrivilegio;
    }
}
