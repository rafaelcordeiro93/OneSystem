/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.war.service.CotacaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
 * @author Rafael
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "COBRANCA_CLASSE_NOME")
public abstract class Cobranca implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_COBRANCA", sequenceName = "SEQ_COBRANCA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_COBRANCA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    protected BigDecimal valor;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date emissao;

    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne(optional = true)
    protected Pessoa pessoa;

    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne(optional = false)
    protected Cotacao cotacao;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    protected String historico;

    @OneToMany(mappedBy = "cobranca")
    private List<TipoDeCobranca> tiposDeCobranca;

    @Enumerated(EnumType.STRING)
    private SituacaoDeCobranca situacaoDeCobranca;

    @NotNull(message = "{filial_not_null}")
    @ManyToOne(optional = false)
    private Filial filial;

    @NotNull(message = "{vencimento_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date vencimento;

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    protected OperacaoFinanceira operacaoFinanceira;

    public Cobranca() {
    }

    public Cobranca(Long id, BigDecimal valor, Date emissao, Pessoa pessoa,
            Cotacao cotacao, String historico,
            SituacaoDeCobranca situacaoDeCobranca, Filial filial,
            Date vencimento, OperacaoFinanceira operacaoFinanceira) {
        this.id = id;
        this.valor = valor;
        this.emissao = emissao == null ? new Date() : emissao;;
        this.pessoa = pessoa;
        this.cotacao = cotacao;
        this.historico = historico;
        this.filial = filial;
        this.vencimento = vencimento;
        this.operacaoFinanceira = operacaoFinanceira;
        if (situacaoDeCobranca != null) {
            this.situacaoDeCobranca = situacaoDeCobranca;
        } else {
            atualizaSituacao();
        }
    }

    public abstract ModalidadeDeCobranca getModalidade();

    public abstract String getDetalhes();

    /**
     * Utilizado no GeradorDeBaixaDeTipoCobranca no método geraBaixas para
     * atualizar a situação da cobrança ao receber o pagamento.
     *
     * Quando a soma do valor dos tipos de cobranca for maior ou igual ao valor
     * da cobrança, a mesma é considerada paga, caso contrário é considerada
     * aberta. Não existe situação Parcial.
     *
     * @date 10/08/2017
     * @author Rafael Fernando Rauber
     * @see GeradorDeBaixaDeTipoCobranca
     */
    public final void atualizaSituacao() {
        if (tiposDeCobranca != null) {
            BigDecimal soma = tiposDeCobranca.stream().map(TipoDeCobranca::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (soma.compareTo(this.valor) >= 0 || (tiposDeCobranca.size() > 0 && this instanceof CobrancaFixa)) {
                situacaoDeCobranca = SituacaoDeCobranca.PAGO;
            } else {
                situacaoDeCobranca = SituacaoDeCobranca.ABERTO;
            }
        } else {
            situacaoDeCobranca = SituacaoDeCobranca.ABERTO;
        }
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public Filial getFilial() {
        return filial;
    }

    public boolean getPossuiPagamento() {
        return tiposDeCobranca == null ? false : tiposDeCobranca.isEmpty() ? false : true;
    }

    @MetodoInacessivelRelatorio
    public Double getValorDouble() {
        return valor.doubleValue();
    }

    public String getValorFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), valor);
    }

    public BigDecimal getValorNaMoedaPadrao() {
        return MoedaFormatter.valorConvertidoNaMoedaPadrao(getValor(), getCotacao());
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

    public List<TipoDeCobranca> getTiposDeCobranca() {
        return tiposDeCobranca;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Long getDias() {
        if (vencimento != null) {
            LocalDate v = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate e = emissao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return ChronoUnit.DAYS.between(e, v);
        }
        return null;
    }

    public DayOfWeek getDiaDaSemana() {
        if (vencimento != null) {
            LocalDate v = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return v.getDayOfWeek();
        }
        return null;
    }

    public String getVencimentoFormatado() {
        if (vencimento != null) {
            SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
        }
        return "";
    }

    public String getVencimentoFormatadoSemHoras() {
        if (vencimento != null) {
            SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
            return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
        }
        return "";
    }

    public Date getEmissao() {
        return emissao;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
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

    public String getTotalNaMoedaPadraoFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getValor());
    }

    public String getTotalNaMoedaPadraoPorExtenso() {
        return StringUtils.primeiraLetraMaiusculaAposEspaco(MoedaFormatter.valorPorExtenso(cotacao.getConta().getMoeda(), getValor()));
    }

    public String getTotalNaMoedaPadraoFormatadoEPorExtenso() {
        return getTotalNaMoedaPadraoFormatado() + " (" + getTotalNaMoedaPadraoPorExtenso() + ")";
    }

    public String getTotalNaMoedaPadraoFormatadoEPorExtensoPrimeiraLinha() {
        if (getTotalNaMoedaPadraoFormatadoEPorExtenso().length() > 95) {
            return getTotalNaMoedaPadraoFormatadoEPorExtenso().substring(0, 95);
        } else {
            return getTotalNaMoedaPadraoFormatadoEPorExtenso();
        }
    }

    public String getTotalNaMoedaPadraoFormatadoEPorExtensoSegundaLinha() {
        if (getTotalNaMoedaPadraoFormatadoEPorExtenso().length() > 95) {
            return getTotalNaMoedaPadraoFormatadoEPorExtenso().substring(95);
        }
        return "";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public Long getIdNota() {
        if (this instanceof CobrancaVariavel) {
            CobrancaVariavel cobrancaVariavel = (CobrancaVariavel) this;
            return cobrancaVariavel.getNota().getId();
        } else {
            return new Long(0);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cobranca other = (Cobranca) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
