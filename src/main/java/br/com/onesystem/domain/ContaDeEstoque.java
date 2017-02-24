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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "contaDeEstoque")
    private List<OperacaoDeEstoque> operacaoDeEstoque;

    public ContaDeEstoque() {
    }

    public ContaDeEstoque(Long id, String nome, List<OperacaoDeEstoque> operacaoDeEstoque) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.operacaoDeEstoque = operacaoDeEstoque;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<OperacaoDeEstoque> getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
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
        return "ContaDeEstoque{" + "id=" + id + ", nome=" + nome + '}';
    }

}
