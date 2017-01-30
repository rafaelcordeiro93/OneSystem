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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CONTADEESTOQUE",
        sequenceName = "SEQ_CONTADEESTOQUE")
public class ContaDeEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONTADEESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    private String nome;
    @ManyToMany(mappedBy = "contaDeEstoque")
    private List<Operacao> operacoes;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_fisica_not_null}")
    private OperacaoFisica operacaoFisica;

    public ContaDeEstoque() {
    }

    public ContaDeEstoque(Long id, String nome, OperacaoFisica operacaoFisica, List<Operacao> operacoes) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.operacaoFisica = operacaoFisica;
        this.operacoes = operacoes;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    public String getNome() {
        return nome;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "operacaoFisica");
        new ValidadorDeCampos<ContaDeEstoque>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ContaDeEstoque)) {
            return false;
        }
        ContaDeEstoque outro = (ContaDeEstoque) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ContaDeEstoque{" + "id=" + id + ", nome=" + nome + ", operacaoFisica=" + operacaoFisica + '}';
    }

}
