/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.GeradorDeBaixaDeFormaCobranca;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_FORMADECOBRANCA",
        sequenceName = "SEQ_FORMADECOBRANCA")
public class FormaDeCobranca implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_FORMADECOBRANCA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cobranca cobranca;

    @ManyToOne
    private Recebimento recebimento;

    @ManyToOne
    private Pagamento pagamento;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    private BigDecimal valor;

    @Min(value = 0, message = "{valor_juros_min}")
    @Column(nullable = true)
    private BigDecimal juros;

    @Min(value = 0, message = "{valor_multa_min}")
    @Column(nullable = true)
    private BigDecimal multa;

    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;

    @Length(min = 0, max = 255, message = "{observacao_length}")
    @Column(nullable = true, length = 255)
    private String observacao;

    @ManyToOne
    private Cotacao cotacao;

    @OneToMany(mappedBy = "formaDeCobranca", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @ManyToOne
    private Caixa caixa;

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira operacaoFinanceira;

    public FormaDeCobranca() {
    }

    public FormaDeCobranca(Long id, Cobranca cobranca, Recebimento recebimento, BigDecimal valor,
            BigDecimal juros, BigDecimal multa, BigDecimal desconto, String observacao, Cotacao cotacao, Pagamento pagamento, Caixa caixa,
            OperacaoFinanceira operacaoFinanceira) throws DadoInvalidoException {
        this.id = id;
        this.cobranca = cobranca;
        this.recebimento = recebimento;
        this.valor = valor;
        this.juros = juros;
        this.multa = multa;
        this.desconto = desconto;
        this.observacao = observacao;
        this.cotacao = cotacao;
        this.pagamento = pagamento;
        this.caixa = caixa;
        this.operacaoFinanceira = operacaoFinanceira;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "juros", "desconto", "multa", "observacao", "operacaoFinanceira");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public void cancela() throws DadoInvalidoException {
        for (Baixa b : baixas) {
            b.cancela();
        }
    }
    
     public void descancelar() throws DadoInvalidoException {
        for (Baixa b : baixas) {
            b.descancela();
        }
    }

    public void geraBaixas() {
        try {
            if (cobranca != null) {
                GeradorDeBaixaDeFormaCobranca gerador = new GeradorDeBaixaDeFormaCobranca(this);
                gerador.geraBaixas();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Long getId() {
        return id;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public String getObservacao() {
        return observacao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public EstadoDeLancamento getEstado() {
        if (recebimento != null) {
            return recebimento.getEstado();
        } else if (pagamento != null) {
            return pagamento.getEstado();
        } else {
            return null;
        }
    }

    public BigDecimal getTotal() {
        BigDecimal v = valor == null ? BigDecimal.ZERO : valor;
        BigDecimal j = juros == null ? BigDecimal.ZERO : juros;
        BigDecimal m = multa == null ? BigDecimal.ZERO : multa;
        BigDecimal d = desconto == null ? BigDecimal.ZERO : desconto;

        return (v.add(j).add(m)).subtract(d);
    }

    public String getTotalFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotal());
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public String getTipoDocumento() {
        if (cobranca != null) {
            if (cobranca instanceof Titulo) {
                return new BundleUtil().getLabel("Titulo");
            } else if (cobranca instanceof Cheque) {
                return new BundleUtil().getLabel("Cheque");
            } else if (cobranca instanceof BoletoDeCartao) {
                return new BundleUtil().getLabel("Boleto_De_Cartao");
            } else {
                return new BundleUtil().getLabel("Credito");
            }
        }
        return "";
    }

    public BigDecimal getTotalNaMoedaPadrao() {
        if (getCobranca() != null) {
            return getTotal().divide(getCotacao().getValor(), 2, BigDecimal.ROUND_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public String getTotalNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(recebimento.getCotacaoPadrao().getConta().getMoeda(), getTotalNaMoedaPadrao());
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof FormaDeCobranca)) {
            return false;
        }
        FormaDeCobranca outro = (FormaDeCobranca) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
