/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.GeradorDeBaixaDeTipoCobranca;
import br.com.onesystem.util.GeradorDeBaixaDeTipoCobrancaFixa;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_TIPODECOBRANCA",
        sequenceName = "SEQ_TIPODECOBRANCA")
public class TipoDeCobranca implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_TIPODECOBRANCA", strategy = GenerationType.SEQUENCE)
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

    @ManyToOne
    private Conta conta;

    @OneToMany(mappedBy = "tipoDeCobranca", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    public TipoDeCobranca() {
    }

    public TipoDeCobranca(Long id, Cobranca cobranca, Recebimento recebimento, BigDecimal valor,
            BigDecimal juros, BigDecimal multa, BigDecimal desconto, String observacao, Cotacao cotacao,
            Conta conta, Pagamento pagamento) throws DadoInvalidoException {
        this.id = id;
        this.cobranca = cobranca;
        this.recebimento = recebimento;
        this.valor = valor;
        this.juros = juros;
        this.multa = multa;
        this.desconto = desconto;
        this.observacao = observacao;
        this.cotacao = cotacao;
        this.conta = conta;
        this.pagamento = pagamento;
        ehValido();
    }

    public void geraBaixas() {
        try {
            if (cobranca != null) {
                GeradorDeBaixaDeTipoCobranca gerador = new GeradorDeBaixaDeTipoCobranca(this);
                gerador.geraBaixas();
            } else {
                GeradorDeBaixaDeTipoCobrancaFixa gerador = new GeradorDeBaixaDeTipoCobrancaFixa(this);
                gerador.geraBaixas();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "juros", "desconto", "multa", "observacao");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
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

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public Conta getConta() {
        return conta;
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

    public String getTipoDocumento() {
        if (cobranca != null) {
            if (cobranca instanceof Titulo) {
                return new BundleUtil().getLabel("Titulo");
            } else if (cobranca instanceof Cheque) {
                return new BundleUtil().getLabel("Cheque");
            } else if (cobranca instanceof BoletoDeCartao) {
                return new BundleUtil().getLabel("Boleto_De_Cartao");
            } else if (cobranca instanceof Credito) {
                return new BundleUtil().getLabel("Credito");
            } else if (cobranca instanceof ReceitaEventual) {
                return new BundleUtil().getLabel("Receita_Eventual");
            } else if (cobranca instanceof ReceitaProvisionada) {
                return new BundleUtil().getLabel("Receita_Provisionada");
            } else if (cobranca instanceof DespesaEventual) {
                return new BundleUtil().getLabel("Despesa_Eventual");
            } else {
                return new BundleUtil().getLabel("Despesa_Provisionada");
            }
        } else {
            return null;
        }
    }

    public BigDecimal getTotalNaMoedaPadrao() {
        if (getCobranca() != null) {
            return getTotal().divide(getCotacao().getValor(), 2, BigDecimal.ROUND_UP);
        } else {
            return BigDecimal.ZERO;
        }
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

    public String getTotalNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(recebimento.getCotacaoPadrao().getConta().getMoeda(), getTotalNaMoedaPadrao());
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof TipoDeCobranca)) {
            return false;
        }
        TipoDeCobranca outro = (TipoDeCobranca) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "TipoDeCobranca{" + "id=" + id + ", cobranca=" + cobranca + ", recebimento=" + recebimento + ", valor=" + valor + ", juros=" + juros + ", multa=" + multa + ", desconto=" + desconto + ", observacao=" + observacao + ", cotacao=" + cotacao + '}';
    }

}
