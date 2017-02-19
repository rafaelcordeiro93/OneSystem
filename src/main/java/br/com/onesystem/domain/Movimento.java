/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_MOVIMENTO",
        sequenceName = "SEQ_MOVIMENTO")
public class Movimento implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_MOVIMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "movimento", cascade = CascadeType.ALL)
    private List<PerfilDeValor> perfilsDeValores;

    @OneToMany(mappedBy = "movimento", cascade = CascadeType.ALL)
    private List<FormaPagamentoRecebimento> formasPagamentosRecebimentos;

    @NotNull(message = "{operacao_financeira_not_null}")
    @Column(nullable = false)
    private OperacaoFinanceira operacaoFinanceira;

    public Movimento() {
    }

    public Movimento(Long id, List<PerfilDeValor> perfilsDeValores, List<FormaPagamentoRecebimento> formasPagamentosRecebimentos, OperacaoFinanceira operacaoFinanceira) {
        this.id = id;
        this.perfilsDeValores = perfilsDeValores;
        this.formasPagamentosRecebimentos = formasPagamentosRecebimentos;
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Long getId() {
        return id;
    }

    public List<PerfilDeValor> getPerfilsDeValores() {
        return perfilsDeValores;
    }

    public List<FormaPagamentoRecebimento> getFormasPagamentosRecebimentos() {
        return formasPagamentosRecebimentos;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Movimento)) {
            return false;
        }
        Movimento outro = (Movimento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Movimento{" + "id=" + id + ", operacaoFinanceira=" + operacaoFinanceira + '}';
    }

}
