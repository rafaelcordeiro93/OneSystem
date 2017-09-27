/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.GeradorDeBaixaDeFormaCobranca;
//import br.com.onesystem.util.GeradorDeBaixaDeFormaCobranca;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.service.CotacaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
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
    private CobrancaVariavel cobrancaVariavel;

    @ManyToOne
    private Movimento movimento;

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

    public FormaDeCobranca(Long id, CobrancaVariavel cobrancaVariavel, Movimento movimento, BigDecimal valor,
            BigDecimal juros, BigDecimal multa, BigDecimal desconto, String observacao, Cotacao cotacao, Caixa caixa,
            OperacaoFinanceira operacaoFinanceira) throws DadoInvalidoException {
        this.id = id;
        this.cobrancaVariavel = cobrancaVariavel;
        this.movimento = movimento;
        this.valor = valor;
        this.juros = juros;
        this.multa = multa;
        this.desconto = desconto;
        this.observacao = observacao;
        this.cotacao = cotacao;
        this.caixa = caixa;
        this.operacaoFinanceira = operacaoFinanceira;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "juros", "desconto", "multa", "observacao", "operacaoFinanceira");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
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

    public Long getId() {
        return id;
    }

    public CobrancaVariavel getCobranca() {
        return cobrancaVariavel;
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

    public Movimento getMovimento() {
        return movimento;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public EstadoDeLancamento getEstado() {
        if (movimento != null) {
            return movimento.getEstado();
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

    @MetodoInacessivelRelatorio
    public Cotacao getCotacaoPadraoDoRecebimentoOuPagamento() {
        if (movimento != null) {
            return movimento.getCotacaoPadrao();
        } else {
            try {
                return new CotacaoService().getCotacaoPadrao(new Date());
            } catch (DadoInvalidoException ex) {
                ex.print();
                return null;
            }
        }
    }

    public String getTipoDocumento() {
        if (cobrancaVariavel != null) {
            if (cobrancaVariavel instanceof Titulo) {
                return new BundleUtil().getLabel("Titulo");
            } else if (cobrancaVariavel instanceof Cheque) {
                return new BundleUtil().getLabel("Cheque");
            } else if (cobrancaVariavel instanceof BoletoDeCartao) {
                return new BundleUtil().getLabel("Boleto_De_Cartao");
            } else {
                return new BundleUtil().getLabel("Credito");
            }
        }
        return "";
    }

    public String getTotalNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(getCotacaoPadraoDoRecebimentoOuPagamento().getConta().getMoeda(), getTotalNaMoedaPadrao());
    }

    public String getJurosNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(getCotacaoPadraoDoRecebimentoOuPagamento().getConta().getMoeda(), getJurosNaMoedaPadrao());
    }

    public String getDescontoNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(getCotacaoPadraoDoRecebimentoOuPagamento().getConta().getMoeda(), getDescontoNaMoedaPadrao());
    }

    public String getMultaNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(getCotacaoPadraoDoRecebimentoOuPagamento().getConta().getMoeda(), getMultaNaMoedaPadrao());
    }

    public String getValorNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(getCotacaoPadraoDoRecebimentoOuPagamento().getConta().getMoeda(), getValorNaMoedaPadrao());
    }

    public BigDecimal getDescontoNaMoedaPadrao() {
        return MoedaFormatter.valorConvertidoNaMoedaPadrao(getDesconto(), getCotacao());
    }

    public BigDecimal getJurosNaMoedaPadrao() {
        return MoedaFormatter.valorConvertidoNaMoedaPadrao(getJuros(), getCotacao());
    }

    public BigDecimal getMultaNaMoedaPadrao() {
        return MoedaFormatter.valorConvertidoNaMoedaPadrao(getMulta(), getCotacao());
    }

    public BigDecimal getTotalNaMoedaPadrao() {
        return MoedaFormatter.valorConvertidoNaMoedaPadrao(getTotal(), getCotacao());
    }

    public BigDecimal getValorNaMoedaPadrao() {
        return MoedaFormatter.valorConvertidoNaMoedaPadrao(getValor(), getCotacao());
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
