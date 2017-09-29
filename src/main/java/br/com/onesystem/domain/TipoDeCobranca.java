/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
//import br.com.onesystem.util.GeradorDeBaixaDeTipoCobranca;
//import br.com.onesystem.util.GeradorDeBaixaDeTipoCobrancaFixa;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Cobranca cobranca;

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

    @ManyToOne
    private Conta conta;

    @OneToMany(mappedBy = "tipoDeCobranca", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    public TipoDeCobranca() {
    }

    public TipoDeCobranca(Long id, Cobranca cobranca, Movimento movimento, BigDecimal valor,
            BigDecimal juros, BigDecimal multa, BigDecimal desconto, String observacao, Cotacao cotacao,
            Conta conta) throws DadoInvalidoException {
        this.id = id;
        this.cobranca = cobranca;
        this.movimento = movimento;
        this.valor = valor;
        this.juros = juros;
        this.multa = multa;
        this.desconto = desconto;
        this.observacao = observacao;
        this.cotacao = cotacao;
        this.conta = conta;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "juros", "desconto", "multa", "observacao");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }
    
    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    public Long getId() {
        return id;
    }

    public Cobranca getCobranca() {
        return cobranca;
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

    public Conta getConta() {
        return conta;
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

    public String getTotalPorExtenso() {
        return StringUtils.primeiraLetraMaiusculaAposEspaco(MoedaFormatter.valorPorExtenso(cotacao.getConta().getMoeda(), getTotal()));
    }

    public String getTotalNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(movimento.getCotacaoPadrao().getConta().getMoeda(), getTotalNaMoedaPadrao());
    }

    public String getJurosNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(movimento.getCotacaoPadrao().getConta().getMoeda(), getJurosNaMoedaPadrao());
    }

    public String getDescontoNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(movimento.getCotacaoPadrao().getConta().getMoeda(), getDescontoNaMoedaPadrao());
    }

    public String getMultaNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(movimento.getCotacaoPadrao().getConta().getMoeda(), getMultaNaMoedaPadrao());
    }

    public String getValorNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(movimento.getCotacaoPadrao().getConta().getMoeda(), getValorNaMoedaPadrao());
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

    public EstadoDeLancamento getEstado() {
        if (movimento != null) {
            return movimento.getEstado();
        } else {
            return null;
        }
    }
    
    public void adiciona(Baixa baixa) {
        if (baixas == null) {
            baixas = new ArrayList<>();
        }
        this.baixas.add(baixa);
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
        return "TipoDeCobranca{" + "id=" + id + ", cobranca=" + cobranca.getId() + ", recebimento=" + movimento.getId() + ", valor=" + valor + ", juros=" + juros + ", multa=" + multa + ", desconto=" + desconto + ", observacao=" + observacao + ", cotacao=" + cotacao + '}';
    }

}
