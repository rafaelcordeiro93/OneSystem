/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_FORMADERECEBIMENTOOUPAGAMENTO", name = "SEQ_FORMADERECEBIMENTOOUPAGAMENTO",
        initialValue = 1, allocationSize = 1)
public class FormaDeRecebimentoOuPagamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FORMADERECEBIMENTOOUPAGAMENTO")
    private Long id;
    @NotNull(message = "{forma_recebimento_not_null}")
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @Min(value = 0, message = "{min_parcelas}")
    private BigDecimal parcelas;
    @Min(value = 0, message = "{min_dinheiro}")
    private BigDecimal dinheiro;
    @Min(value = 0, message = "{min_credito}")
    private BigDecimal credito;
    @Min(value = 0, message = "{min_cheque}")
    private BigDecimal cheque;
    @Min(value = 0, message = "{min_cartao}")
    private BigDecimal cartao;
    @Min(value = 0, message = "{min_aFaturar}")
    private BigDecimal aFaturar;
    @OneToOne
    private NotaEmitida notaEmitida;
    @ManyToOne
    private Moeda moeda;

    public FormaDeRecebimentoOuPagamento() {
    }

    public FormaDeRecebimentoOuPagamento(Long id, FormaDeRecebimento formaDeRecebimento, BigDecimal parcelas, BigDecimal dinheiro, BigDecimal credito, BigDecimal cheque, BigDecimal cartao, BigDecimal aFaturar, NotaEmitida notaEmitida, Moeda moeda) throws DadoInvalidoException {
        this.id = id;
        this.formaDeRecebimento = formaDeRecebimento;
        this.parcelas = parcelas;
        this.dinheiro = dinheiro;
        this.credito = credito;
        this.cheque = cheque;
        this.cartao = cartao;
        this.aFaturar = aFaturar;
        this.notaEmitida = notaEmitida;
        this.moeda = moeda;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public BigDecimal getParcelas() {
        return parcelas;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public BigDecimal getCheque() {
        return cheque;
    }

    public BigDecimal getCartao() {
        return cartao;
    }

    public BigDecimal getaFaturar() {
        return aFaturar;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("formaDeRecebimento", "parcelas", "dinheiro",
                "credito", "cheque", "cartao", "aFaturar");
        new ValidadorDeCampos<FormaDeRecebimentoOuPagamento>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof FormaDeRecebimentoOuPagamento)) {
            return false;
        }
        FormaDeRecebimentoOuPagamento outro = (FormaDeRecebimentoOuPagamento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
