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
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_OPERACAODEESTOQUE",
        sequenceName = "SEQ_OPERACAODEESTOQUE")
public class OperacaoDeEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_OPERACAODEESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private ContaDeEstoque contaDeEstoque;
    @ManyToOne
    private Operacao operacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_fisica_not_null}")
    private OperacaoFisica operacaoFisica;

    public OperacaoDeEstoque() {
    }

    public OperacaoDeEstoque(Long id, ContaDeEstoque contaDeEstoque, Operacao operacoes, OperacaoFisica operacaoFisica) throws DadoInvalidoException {
        this.id = id;
        this.contaDeEstoque = contaDeEstoque;
        this.operacaoFisica = operacaoFisica;
        this.operacao = operacoes;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public void setContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
    }
    
    public ContaDeEstoque getContaDeEstoque() {
        return contaDeEstoque;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("operacaoFisica");
        new ValidadorDeCampos<OperacaoDeEstoque>().valida(this, campos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (OperacaoDeEstoque.class != obj.getClass()) {
            return false;
        }
        final OperacaoDeEstoque other = (OperacaoDeEstoque) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OperacaoDeEstoque{" + "id=" + id + ", contaDeEstoque=" + contaDeEstoque + ", operacoes=" + operacao + ", operacaoFisica=" + operacaoFisica + '}';
    }


}
