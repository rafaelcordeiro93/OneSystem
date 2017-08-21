/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CFOP", sequenceName = "SEQ_CFOP")
public class Cfop implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CFOP", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{CFOP_not_null}")
    @Column(unique = true)
    private Long cfop;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 100, min = 4, message = "{nome_lenght}")
    private String nome;
    @Enumerated(EnumType.STRING)
    private OperacaoFisica operacaoFisica;
    @Column(length = 1000)
    private String texto;
    @OneToMany(mappedBy = "cfop")
    private List<SituacaoFiscal> situacoesFiscais;

    public Cfop() {
    }

    public Cfop(Long id, Long cfop, String nome, OperacaoFisica operacaoFisica, String texto) throws DadoInvalidoException {
        this.id = id;
        this.cfop = cfop;
        this.nome = nome;
        this.operacaoFisica = operacaoFisica;
        this.texto = texto;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("cfop", "nome");
        new ValidadorDeCampos<Cfop>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public Long getCfop() {
        return cfop;
    }

    public String getNome() {
        return nome;
    }

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
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
        final Cfop other = (Cfop) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CFOP{" + "id=" + id + ", cfop=" + cfop + ", nome=" + nome + ", operacao=" + operacaoFisica + ", texto=" + texto + '}';
    }

}
