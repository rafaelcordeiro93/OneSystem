/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.OperacaoDeEstoqueBV;
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
    private List<OperacaoDeEstoque> operacaoDeEstoque = new ArrayList<>();

    public ContaDeEstoque() {
    }

    public ContaDeEstoque(Long id, List<OperacaoDeEstoque> operacoesDeEstoque,String nome) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.operacaoDeEstoque = operacoesDeEstoque;
        ehValido();
    }

    public void adiciona(OperacaoDeEstoque operacao) throws DadoInvalidoException {
        validaOperacaoDeEstoqueExistente(false, operacao);
        operacao.setContaDeEstoque(this);
        this.operacaoDeEstoque.add(operacao);
    }

    public void atualiza(OperacaoDeEstoque selecionado, OperacaoDeEstoque operacao) throws DadoInvalidoException {
        validaOperacaoDeEstoqueExistente(true, operacao);
        this.operacaoDeEstoque.set(this.operacaoDeEstoque.indexOf(selecionado), operacao);
    }

    public void remove(OperacaoDeEstoque selecionado) {
        this.operacaoDeEstoque.remove(selecionado);
    }

    private void validaOperacaoDeEstoqueExistente(boolean update, OperacaoDeEstoque operacao) throws EDadoInvalidoException {
        if (!update) {

            for (OperacaoDeEstoque lista : operacaoDeEstoque) {
                if (operacao.getOperacao().equals(lista.getOperacao())) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_ja_existe"));
                }
            }
        } else {
            boolean valida = false;
            for (OperacaoDeEstoque lista : operacaoDeEstoque) {
                if (operacao.getOperacao().equals(lista.getOperacao())
                        && !operacao.getId().equals(lista.getId())) {
                    valida = true;
                    break;
                }
            }
            if (valida) {

                throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_ja_existe"));
            }

        }
    }

    private void geraOperacaoDeEstoque() {
        operacaoDeEstoque.forEach(o -> o.setContaDeEstoque(this));
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
