package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.domain.Movimento;
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
public class Baixa implements Serializable {

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
    
    @ManyToOne
    private Movimento movimento;

    public Baixa() {
    }

    public Baixa(Long id, BigDecimal valor, Date emissao, Date dataCompensacao, String historico,
            OperacaoFinanceira tipoMovimentacaoFinanceira, Pessoa pessoa, TipoDespesa despesa,
            Cotacao cotacao, TipoReceita receita, Cambio cambio, Transferencia transferencia,
            Recepcao recepcao, ValorPorCotacao valorPorCotacao,
            TipoDeCobranca tipoDeCobranca, FormaDeCobranca formaDeCobranca, Caixa caixa, 
            DepositoBancario depositoBancario, SaqueBancario saqueBancario, 
            LancamentoBancario lancamentoBancario, CambioEmpresa cambioEmpresa, 
            EstadoDeBaixa estado, Filial filial, Movimento movimento) throws DadoInvalidoException {
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
        this.valorPorCotacao = valorPorCotacao;
        this.tipoDeCobranca = tipoDeCobranca;
        this.formaDeCobranca = formaDeCobranca;
        this.caixa = caixa;
        this.depositoBancario = depositoBancario;
        this.saqueBancario = saqueBancario;
        this.cambioEmpresa = cambioEmpresa;
        this.lancamentoBancario = lancamentoBancario;
        this.filial = filial;
        this.movimento = movimento;
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
        if (tipoDeCobranca != null && tipoDeCobranca.getCobranca() instanceof Titulo) {
            ((Titulo) tipoDeCobranca.getCobranca()).cancelarSaldoDeBaixa(valor);
        }
        if (tipoDeCobranca != null && tipoDeCobranca.getCobranca() instanceof CobrancaVariavel) {
            ((CobrancaVariavel) tipoDeCobranca.getCobranca()).atualizaSituacao();
        }

        this.estado = EstadoDeBaixa.CANCELADO;

        this.dataCancelamento = new Date();
    }

    public void descancela() throws EDadoInvalidoException {
        if (tipoDeCobranca != null && tipoDeCobranca.getCobranca() instanceof Titulo) {
            ((Titulo) tipoDeCobranca.getCobranca()).descancelarSaldoDeBaixa(valor);
        }
        if (tipoDeCobranca != null && tipoDeCobranca.getCobranca() instanceof CobrancaVariavel) {
            ((CobrancaVariavel) tipoDeCobranca.getCobranca()).atualizaSituacao();
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
        if (tipoDeCobranca != null && tipoDeCobranca.getCobranca() instanceof CobrancaFixa) {
            return ((CobrancaFixa) tipoDeCobranca.getCobranca()).getReferencia();
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

    public Date getUltimoPagamento() {
        return emissao;
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public Long getDocumentoOriginal() {
        if (cambio != null) {
            return this.cambio.getId();
        } else if (recepcao != null && tipoDeCobranca.getCobranca() instanceof Titulo) {
            return this.recepcao.getId();
        } else if (recepcao != null && !(tipoDeCobranca.getCobranca() instanceof Titulo)) {
            return ((Titulo) tipoDeCobranca.getCobranca()).getId();
        } else {
            return this.id;
        }
    }

    public Long getIdOrigem() {
        if (cambio != null) {
            return cambio.getId();
        } else if (recepcao != null) {
            return recepcao.getId();
        } else if (despesa != null) {
            return despesa.getId();
        } else if (receita != null) {
            return receita.getId();
        } else if (tipoDeCobranca != null) {
            return tipoDeCobranca.getCobranca().getId();
        } else {
            return getId();
        }
    }

    public String getOrigem() {
        if (cambio != null) {
            return TipoOperacao.CAMBIO.getNome();
        } else if (recepcao != null) {
            return TipoOperacao.RECEPCAO.getNome();
        } else if (despesa != null) {
            return despesa.getNome();
        } else if (receita != null) {
            return receita.getNome();
        } else if (tipoDeCobranca.getCobranca() instanceof Titulo) {
            return TipoOperacao.TITULO.getNome();
        } else if (tipoDeCobranca.getCobranca() instanceof DespesaProvisionada) {
            return ((DespesaProvisionada) tipoDeCobranca.getCobranca()).getTipoDespesa().getNome();
        } else if (tipoDeCobranca.getCobranca() instanceof ReceitaProvisionada) {
            return ((ReceitaProvisionada) tipoDeCobranca.getCobranca()).getPessoa().getNome();
        } else {
            return TipoOperacao.AVULSO.getNome();
        }
    }

    public BigDecimal getValorBaixado() {
        if (tipoDeCobranca.getCobranca() instanceof Titulo) {
            return ((Titulo) tipoDeCobranca.getCobranca()).getValorBaixado();
        } else {
            return getValor();
        }
    }

    @Override
    public String toString() {

        return "Baixa{" + "id=" + id + ",valor=" + valor + ", historico="
                + historico + ", emissao=" + emissao + ", naturezaFinanceira="
                + operacaoFinanceira + ", cotacao=" + (cotacao != null ? cotacao.getId() : null)
                + ", despesa=" + (despesa != null ? despesa.getId() : null) + ", receita="
                + (receita != null ? receita.getId() : null) + ", pessoa=" + (pessoa != null ? pessoa.getId() : null)
                + ", cambio=" + (cambio != null ? cambio.getId() : null)
                + ", transferencia=" + (transferencia != null ? transferencia.getId() : null)
                + ", recepcao=" + (recepcao != null ? recepcao.getId() : null) + ", estado=" + estado + '}';
    }

}
