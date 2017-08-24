/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.util.GeradorDeBaixaDeTipoCobrancaFixa;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@DiscriminatorColumn(name = "COBRANCAFIXA_CLASSE_NOME")
public abstract class CobrancaFixa implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_COBRANCAFIXA", sequenceName = "SEQ_COBRANCAFIXA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_COBRANCAFIXA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    protected BigDecimal valor;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @ManyToOne(optional = true)
    private Pessoa pessoa;

    @NotNull(message = "cotacao_not_null")
    @ManyToOne(optional = false)
    private Cotacao cotacao;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    private String historico;

    @OneToMany(mappedBy = "cobrancaFixa", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @NotNull(message = "{referencia_not_null}")
    @Temporal(TemporalType.DATE)
    private Date referencia;

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira operacaoFinanceira;

    @OneToMany(mappedBy = "cobrancaFixa")
    private List<TipoDeCobranca> tiposDeCobranca;

    @NotNull(message = "{vencimento_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    @Enumerated(EnumType.STRING)
    private SituacaoDeCobranca situacaoDeCobranca;

    public CobrancaFixa() {
    }

    public CobrancaFixa(Long id, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico,
            List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira, BigDecimal valor, Date vencimento,
            Date referencia, SituacaoDeCobranca situacaoDeCobranca) throws DadoInvalidoException {
        this.id = id;
        this.valor = valor;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.cotacao = cotacao;
        this.historico = historico;
        this.operacaoFinanceira = operacaoFinanceira;
        this.baixas = baixas;
        this.vencimento = vencimento;
        this.referencia = referencia;
        if (situacaoDeCobranca != null) {
            this.situacaoDeCobranca = situacaoDeCobranca;
        } else {
            atualizaSituacao();
        }
        ehAbstracaoValida();
    }

    private final void ehAbstracaoValida() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "historico", "cotacao", "operacaoFinanceira", "referencia");
        new ValidadorDeCampos<CobrancaFixa>().valida(this, campos);
    }

    public void adiciona(Baixa baixa) {
        if (baixas == null) {
            baixas = new ArrayList<>();
        }
        this.baixas.add(baixa);
    }

    public abstract String getDetalhes();

    public Long getId() {
        return id;
    }

    @MetodoInacessivelRelatorio
    public Double getValorDouble() {
        return valor.doubleValue();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public String getHistorico() {
        return historico;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Date getReferencia() {
        return referencia;
    }

    /**
     * Utilizado no GeradorDeBaixaDeTipoCobrancaFixa no método geraBaixas para
     * atualizar a situação da cobrança ao receber o pagamento.
     *
     * Quando a soma do valor das baixas for maior ou igual ao valor da
     * cobrança, a mesma é considerada paga, caso contrário é considerada
     * aberta. Não existe situação Parcial.
     *
     * @date 11/08/2017
     * @author Rafael Fernando Rauber
     * @see GeradorDeBaixaDeTipoCobrancaFixa
     */
    public final void atualizaSituacao() {
        if (baixas != null) {
            BigDecimal soma = baixas.stream().map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (soma.compareTo(this.valor) >= 0) {
                situacaoDeCobranca = SituacaoDeCobranca.PAGO;
            } else {
                situacaoDeCobranca = SituacaoDeCobranca.ABERTO;
            }
        } else {
            situacaoDeCobranca = SituacaoDeCobranca.ABERTO;
        }
    }

    public Long getDias() {
        LocalDate v = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate e = getEmissao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(e, v);
    }

    public DayOfWeek getDiaDaSemana() {
        LocalDate v = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return v.getDayOfWeek();
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
    }

    public String getVencimentoFormatadoSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
    }

    public String getValorFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), valor);
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public SituacaoDeCobranca getSituacaoDeCobranca() {
        return situacaoDeCobranca;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof CobrancaFixa)) {
            return false;
        }
        CobrancaFixa outro = (CobrancaFixa) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
