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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_fisica_not_null}")
    private OperacaoFisica operacaoFisica;

    public ContaDeEstoque(Long id, Operacao operacao, OperacaoFisica operacaoFisica) throws DadoInvalidoException {
        this.id = id;
        this.operacao = operacao;
        this.operacaoFisica = operacaoFisica;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("operacao", "operacaoFisica");
        new ValidadorDeCampos<ContaDeEstoque>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Conta)) {
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
        return "ContaDeEstoque{" + "id=" + id + ", operacao=" + operacao + ", operacaoFisica=" + operacaoFisica + '}';
    }

}
