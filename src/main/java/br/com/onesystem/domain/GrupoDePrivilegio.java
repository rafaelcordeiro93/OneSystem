/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.CaseType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael-Pc
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_GRUPOPRIVILEGIO", name = "SEQ_GRUPOPRIVILEGIO")
public class GrupoDePrivilegio implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_GRUPOPRIVILEGIO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 3, max = 40, message = "{nome_lenght}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{nome_type_letter_space}")
    private String nome;
    @OneToMany(mappedBy = "grupoPrivilegio", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Privilegio> listaPrivilegios;
    @OneToMany(mappedBy = "grupoPrivilegio")
    private List<Usuario> listaDeUsuarios;

    public GrupoDePrivilegio() {
    }

    public GrupoDePrivilegio(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setListaPrivilegios(List<Privilegio> listaPrivilegios) {
        this.listaPrivilegios = listaPrivilegios;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Privilegio> getListaPrivilegios() {
        return listaPrivilegios;
    }

    public void instanciaListaPrivilegio() {
        listaPrivilegios = new ArrayList<Privilegio>();
    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof GrupoDePrivilegio)) {
            return false;
        }
        GrupoDePrivilegio outro = (GrupoDePrivilegio) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "GrupoDePrivilegio: " + id + " - " + nome;
    }
}
