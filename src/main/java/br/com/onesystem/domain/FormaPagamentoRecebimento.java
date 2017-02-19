/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "FORMA_PAGAMENTO_RECEBIMENTO_CLASSE")
public abstract class FormaPagamentoRecebimento implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_FORMAPAGAMENTORECEBIMENTOPAGAMENTO", sequenceName = "SEQ_FORMAPAGAMENTORECEBIMENTOPAGAMENTO",
        allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "SEQ_FORMAPAGAMENTORECEBIMENTOPAGAMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    protected BigDecimal valor;

    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;

    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    private String historico;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne(optional = false)
    private Cotacao cotacao;

    @ManyToOne
    private Movimento movimento;

    public FormaPagamentoRecebimento() {
    }

    public FormaPagamentoRecebimento(Long id, Date emissao, BigDecimal valor, BigDecimal desconto,
            BigDecimal acrescimo, String historico, Date vencimento, Cotacao cotacao) {
        this.id = id;
        this.emissao = emissao;
        this.valor = valor;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.historico = historico;
        this.vencimento = vencimento;
        this.cotacao = cotacao;
    }

    public Long getId() {
        return id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public String getHistorico() {
        return historico;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(getVencimento());
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof FormaPagamentoRecebimento)) {
            return false;
        }
        FormaPagamentoRecebimento outro = (FormaPagamentoRecebimento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }
}
