/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_PAIS", name = "SEQ_PAIS")
public class Pais implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_PAIS", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 100, min = 4, message = "{nome_lenght}")
    private String nome;
    private Long codigoPais;
    private Long codigoReceita;
    @OneToMany(mappedBy = "pais")
    private List<Estado> estados;

    public Pais() {
    }

    public Pais(Long id, String nome, Long codigoPais, Long codigoReceita) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.codigoPais = codigoPais;
        this.codigoReceita = codigoReceita;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Pais>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Long getCodigoPais() {
        return codigoPais;
    }

    public Long getCodigoReceita() {
        return codigoReceita;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pais other = (Pais) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pais{" + "id=" + id + ", nome=" + nome + ", codigoPais=" + codigoPais + ", codigoReceita=" + codigoReceita + '}';
    }

}
