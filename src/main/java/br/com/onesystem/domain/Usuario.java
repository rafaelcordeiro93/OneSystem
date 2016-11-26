/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
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
    @OneToOne(optional = false)
    private Pessoa pessoa;
    @NotNull(message = "{email_not_null}")
    @Email(message = "{email_invalido}")
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @NotNull(message = "{senha_not_null}")
    @Length(min = 8, max = 100, message = "{senha_length}")
    @Column(nullable = false)
    private String senha;
    @NotNull(message = "{grupo_privilegio_not_null}")
    @ManyToOne(optional = false)
    private GrupoDePrivilegio grupoPrivilegio;
    private boolean supervisor = false;

    public Usuario() {
    }

    public Usuario(Long id, Pessoa pessoa, String email, String senha, GrupoDePrivilegio grupoDePrivilegio,
            boolean supervisor) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.email = email;
        this.senha = senha;
        this.grupoPrivilegio = grupoDePrivilegio;
        this.supervisor = supervisor;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getEmail() {
        return email;
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
        List<String> campos = Arrays.asList("grupoPrivilegio", "email", "senha");
        new ValidadorDeCampos<Usuario>().valida(this, campos);
    }
    
    @Override
    public String toString() {
        return "Usuário - " + "id: " + id + " - pessoa: " + pessoa.getId() + " - login: " + email + " - senha: " + senha
                + " - grupo de privilégio: " + grupoPrivilegio;
    }
}
