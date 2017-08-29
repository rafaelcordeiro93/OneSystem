package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.Movimento;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.NumberUtils;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rauber
 */
@Entity
@SequenceGenerator(name = "SEQ_BAIXA", sequenceName = "SEQ_BAIXA",
        allocationSize = 1, initialValue = 1)
public class Baixa implements Serializable, Movimento {

    @Id
    @GeneratedValue(generator = "SEQ_BAIXA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = true)
    private BigDecimal valor = BigDecimal.ZERO;

    @Length(min = 0, max = 255, message = "{historico_length}")
    @Column(nullable = true, length = 255)
    private String historico = "";

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = Calendar.getInstance().getTime();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCompensacao;

    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira operacaoFinanceira;

    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne(optional = false)
    private Cotacao cotacao;

    @OneToOne
    private ValorPorCotacao valorPorCotacao;

    @ManyToOne
    private Cobranca cobranca;

    @ManyToOne
    private TipoDespesa despesa;

    @ManyToOne
    private TipoReceita receita;

    @ManyToOne
    private Pessoa pessoa;

    @ManyToOne
    private Cambio cambio;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Transferencia transferencia;

    @ManyToOne
    private Recepcao recepcao;

    @Enumerated(EnumType.STRING)
    private EstadoDeBaixa estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCancelamento;

    @ManyToOne
    private TipoDeCobranca tipoDeCobranca;

    @ManyToOne
    private FormaDeCobranca formaDeCobranca;

    @ManyToOne
    private Caixa caixa;

    @ManyToOne
    private SaqueBancario saqueBancario;

    @ManyToOne
    private DepositoBancario depositoBancario;

    @ManyToOne
    private CambioEmpresa cambioEmpresa;

    @ManyToOne
    private LancamentoBancario lancamentoBancario;

    @Enumerated(EnumType.STRING)
    private NaturezaFinanceira naturezaFinanceira;
    
    @NotNull(message = "{filial_not_null}")
    @ManyToOne
    private Filial filial;

    public Baixa() {
    }

    public Baixa(Long id, BigDecimal valor, Date emissao, Date dataCompensacao, String historico,
            OperacaoFinanceira tipoMovimentacaoFinanceira, Pessoa pessoa, TipoDespesa despesa,
            Cotacao cotacao, TipoReceita receita, Cambio cambio, Transferencia transferencia,
            Recepcao recepcao, Cobranca cobranca, ValorPorCotacao valorPorCotacao,
            TipoDeCobranca tipoDeCobranca, FormaDeCobranca formaDeCobranca, Caixa caixa, 
            DepositoBancario depositoBancario, SaqueBancario saqueBancario, 
            LancamentoBancario lancamentoBancario, CambioEmpresa cambioEmpresa, 
            EstadoDeBaixa estado, Filial filial) throws DadoInvalidoException {
        this.id = id;
        setEstado(estado);
        this.valor = valor;
        this.emissao = emissao;
        this.dataCompensacao = dataCompensacao;
        this.historico = historico;
        this.pessoa = pessoa;
        this.operacaoFinanceira = tipoMovimentacaoFinanceira;
        this.despesa = despesa;
        this.cotacao = cotacao;
        this.receita = receita;
        this.cambio = cambio;
        this.transferencia = transferencia;
        this.recepcao = recepcao;
        this.cobranca = cobranca;
        this.valorPorCotacao = valorPorCotacao;
        this.tipoDeCobranca = tipoDeCobranca;
        this.formaDeCobranca = formaDeCobranca;
        this.caixa = caixa;
        this.depositoBancario = depositoBancario;
        this.saqueBancario = saqueBancario;
        this.cambioEmpresa = cambioEmpresa;
        this.lancamentoBancario = lancamentoBancario;
        this.filial = filial;
        if (receita != null) {
            naturezaFinanceira = NaturezaFinanceira.RECEITA;
        } else if (despesa != null) {
            naturezaFinanceira = NaturezaFinanceira.DESPESA;
        }
        ehValido();
    }

    private void setEstado(EstadoDeBaixa es) {
        if (es == null) {
            this.estado = EstadoDeBaixa.EM_DEFINICAO;
        } else {
            this.estado = es;
        }
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("cotacao", "historico", "filial");
        new ValidadorDeCampos<Baixa>().valida(this, campos);
    }

    public void atualizaValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Baixa)) {
            return false;
        }
        Baixa outro = (Baixa) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public String getHistorico() {
        return historico;
    }

    public LancamentoBancario getLancamentoBancario() {
        return lancamentoBancario;
    }

    public BigDecimal getSaldo(BigDecimal saldoAtual) {
        if (operacaoFinanceira == OperacaoFinanceira.ENTRADA) {
            return saldoAtual.add(this.getValor());
        } else if (operacaoFinanceira == OperacaoFinanceira.SAIDA) {
            return saldoAtual.subtract(this.getValor());
        } else {
            return saldoAtual;
        }
    }

    public void cancela() throws EDadoInvalidoException {
        if (getCambio() != null) {
            throw new EDadoInvalidoException("Baixas com referências de câmbio não podem ser canceladas!");
        }
        if (cobranca instanceof Titulo) {
            ((Titulo) cobranca).cancelarSaldoDeBaixa(valor);
        }
        if (cobranca instanceof CobrancaVariavel) {
            ((CobrancaVariavel) cobranca).atualizaSituacao();
        }

        this.estado = EstadoDeBaixa.CANCELADO;

        this.dataCancelamento = new Date();
    }

    public void descancela() throws EDadoInvalidoException {
        if (cobranca instanceof Titulo) {
            ((Titulo) cobranca).descancelarSaldoDeBaixa(valor);
        }
        if (cobranca instanceof CobrancaVariavel) {
            ((CobrancaVariavel) cobranca).atualizaSituacao();
        }
        this.estado = EstadoDeBaixa.EFETIVADO;
        this.dataCancelamento = null;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), operacaoFinanceira == OperacaoFinanceira.SAIDA ? (getValor().multiply(new BigDecimal(-1))) : getValor());
    }

    public String getValorFormatadoSemNegativos() {
        return cotacao.getConta().getMoeda().getSigla() + " " + NumberUtils.format((getValor()));
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public Long getId() {
        return id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return emissaoFormatada.format(getEmissao().getTime());
    }

    public String getCompensacaoFormatada() {
        SimpleDateFormat compensacaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        if (getDataCompensacao() != null) {
            return compensacaoFormatada.format(getDataCompensacao().getTime());
        } else {
            return getEmissaoFormatada();
        }
    }

    public NaturezaFinanceira getNaturezaFinanceira() {
        return naturezaFinanceira;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public ValorPorCotacao getValorPorCotacao() {
        return valorPorCotacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public SaqueBancario getSaqueBancario() {
        return saqueBancario;
    }

    public DepositoBancario getDepositoBancario() {
        return depositoBancario;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public TipoReceita getReceita() {
        return receita;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public Date getDataCompetencia() {
        if (cobranca != null && cobranca instanceof CobrancaFixa) {
            return ((CobrancaFixa) cobranca).getReferencia();
        } else {
            return emissao;
        }
    }

    public TipoDeCobranca getTipoDeCobranca() {
        return tipoDeCobranca;
    }

    public FormaDeCobranca getFormaDeCobranca() {
        return formaDeCobranca;
    }

    @Override
    public Date getVencimento() {
        return null;
    }

    public EstadoDeBaixa getEstado() {
        return estado;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
    
    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    @Override
    public Date getUltimoPagamento() {
        return emissao;
    }

    public Long getDocumentoOriginal() {
        if (cambio != null) {
            return this.cambio.getId();
        } else if (recepcao != null && cobranca instanceof Titulo) {
            return this.recepcao.getId();
        } else if (recepcao != null && !(cobranca instanceof Titulo)) {
            return ((Titulo) cobranca).getId();
        } else {
            return this.id;
        }
    }

    @Override
    public Long getIdOrigem() {
        if (cambio != null) {
            return cambio.getId();
        } else if (recepcao != null) {
            return recepcao.getId();
        } else if (despesa != null) {
            return despesa.getId();
        } else if (receita != null) {
            return receita.getId();
        } else if (cobranca != null) {
            return cobranca.getId();
        } else {
            return getId();
        }
    }

    @Override
    public String getOrigem() {
        if (cambio != null) {
            return TipoOperacao.CAMBIO.getNome();
        } else if (recepcao != null) {
            return TipoOperacao.RECEPCAO.getNome();
        } else if (despesa != null) {
            return despesa.getNome();
        } else if (receita != null) {
            return receita.getNome();
        } else if (cobranca instanceof Titulo) {
            return TipoOperacao.TITULO.getNome();
        } else if (cobranca instanceof DespesaProvisionada) {
            return ((DespesaProvisionada) cobranca).getTipoDespesa().getNome();
        } else if (cobranca instanceof ReceitaProvisionada) {
            return ((ReceitaProvisionada) cobranca).getPessoa().getNome();
        } else {
            return TipoOperacao.AVULSO.getNome();
        }
    }

    @Override
    public BigDecimal getValorBaixado() {
        if (cobranca instanceof Titulo) {
            return ((Titulo) cobranca).getValorBaixado();
        } else {
            return getValor();
        }
    }

    @Override
    public String toString() {

        return "Baixa{" + "id=" + id + ",valor=" + valor + ", historico="
                + historico + ", emissao=" + emissao + ", naturezaFinanceira="
                + operacaoFinanceira + ", cotacao=" + (cotacao != null ? cotacao.getId() : null)
                + ", perfilDeValor=" + (cobranca != null ? cobranca.getId() : null)
                + ", despesa=" + (despesa != null ? despesa.getId() : null) + ", receita="
                + (receita != null ? receita.getId() : null) + ", pessoa=" + (pessoa != null ? pessoa.getId() : null)
                + ", cambio=" + (cambio != null ? cambio.getId() : null)
                + ", transferencia=" + (transferencia != null ? transferencia.getId() : null)
                + ", recepcao=" + (recepcao != null ? recepcao.getId() : null) + ", estado=" + estado + '}';
    }

}
