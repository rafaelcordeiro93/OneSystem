package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_TITULO",
        sequenceName = "SEQ_TITULO")
public class Titulo implements Serializable, RelatorioContaAbertaImpl {

    @Id
    @GeneratedValue(generator = "SEQ_TITULO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = true)
    private Pessoa pessoa;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    private String historico;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private BigDecimal saldo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = Calendar.getInstance().getTime();

    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoPagamento = Calendar.getInstance().getTime();

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira unidadeFinanceira;

    @OneToOne
    private Recepcao recepcao;

    @ManyToOne
    private Cambio cambio;

    @OneToMany(mappedBy = "titulo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Baixa> baixas = new ArrayList<Baixa>();

    @NotNull(message = "{tipoFormaPagRec_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoFormaPagRec tipoFormaPagRec;

    @NotNull(message = "moeda_not_null")
    @ManyToOne(optional = false)
    private Moeda moeda;

    public Titulo() {
    }

    public Titulo(Long id, Pessoa pessoa, String historico, BigDecimal valor, BigDecimal saldo, Date emissao,
            OperacaoFinanceira unidadeFinanceira, TipoFormaPagRec tipoFormaPagRec, Date vencimento, Recepcao recepcao,
            Cambio cambio, Date ultimoPagamento, Moeda moeda) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.historico = historico;
        this.valor = valor;
        this.emissao = emissao;
        this.unidadeFinanceira = unidadeFinanceira;
        this.saldo = saldo;
        this.tipoFormaPagRec = tipoFormaPagRec;
        this.recepcao = recepcao;
        this.cambio = cambio;
        this.ultimoPagamento = ultimoPagamento;
        this.moeda = moeda;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "historico", "valor", "unidadeFinanceira", "moeda");
        new ValidadorDeCampos<Titulo>().valida(this, campos);
    }

    public void cancelarSaldoDeBaixa(BigDecimal valor) {
        this.saldo = saldo.add(valor);
    }

    public BigDecimal atualizaSaldo(BigDecimal valor) throws EDadoInvalidoException {
        if (valor.compareTo(saldo) == 1) {
            throw new EDadoInvalidoException("O valor deve ser menor ou igual ao saldo!");
        } else {
            this.saldo = saldo.subtract(valor);
            atualizaPagamento(new Date());
        }
        return valor;
    }

    private void atualizaPagamento(Date data) {
        this.ultimoPagamento = data;
    }

    public void setValor(BigDecimal valor) throws EDadoInvalidoException {
        if (saldo.compareTo(this.valor) == 0) {
            this.valor = valor;
        } else {
            throw new EDadoInvalidoException("O valor só pode ser alterado, quando não possuir recebimento ou pagamento.");
        }
    }

    public void setSaldo(BigDecimal saldo) throws EDadoInvalidoException {
        if (saldo.compareTo(valor) == 0) {
            this.saldo = saldo;
        } else {
            throw new EDadoInvalidoException("O saldo só pode ser alterado, quando não possuir recebimento ou pagamento.");
        }
    }

    public BigDecimal getValorBaixado() {
        return valor.subtract(saldo);
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getHistorico() {
        return historico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public Date getUltimoPagamento() {
        return ultimoPagamento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public OperacaoFinanceira getUnidadeFinanceira() {
        return unidadeFinanceira;
    }

    public TipoFormaPagRec getTipoFormaPagRec() {
        return tipoFormaPagRec;
    }

    public Long getIdOrigem() {
        return cambio != null ? cambio.getId() : recepcao.getId();
    }

    public String getOrigem() {
        return cambio != null ? TipoOperacao.CAMBIO.getNome() : TipoOperacao.RECEPCAO.getNome();
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public String getSaldoFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getSaldo());
    }

    public String getValorFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getValor());
    }

    public String getVencimentoFormatadoSemHoras() {
        SimpleDateFormat vencimentoFormadado = new SimpleDateFormat("dd/MM/yyyy");
        return getVencimento() != null ? vencimentoFormadado.format(getVencimento().getTime()) : "";
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Titulo)) {
            return false;
        }
        Titulo outro = (Titulo) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
