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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_ESTADO",
        sequenceName = "SEQ_ESTADO")
public class Estado implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_ESTADO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 100, min = 4, message = "{nome_lenght}")
    private String nome;
    private String sigla;
    @ManyToOne
    private Pais pais;
    @OneToMany(mappedBy = "estado")
    private List<Cidade> cidades;
    @OneToMany(mappedBy = "estado")
    private List<SituacaoFiscal> situacoesFiscais;

    public Estado() {
    }

    public Estado(Long id, String nome, String sigla, Pais pais) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.pais = pais;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Estado>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public Pais getPais() {
        return pais;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    @Override
    public String toString() {
        return "Estado{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", pais=" + pais + '}';
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
        final Estado other = (Estado) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
