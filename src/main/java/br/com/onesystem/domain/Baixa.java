package br.com.onesystem.domain;

import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.Movimento;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.NumberUtils;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    @Column(nullable = true)
    private Integer numeroParcela;

    @Column(nullable = false)
    private BigDecimal juros = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal multas = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Length(min = 0, max = 255, message = "{historico_length}")
    @Column(nullable = true, length = 255)
    private String historico = "";

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = Calendar.getInstance().getTime();

    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira naturezaFinanceira;

    @NotNull(message = "{conta_not_null}")
    @ManyToOne(optional = false)
    private Conta conta;

    @ManyToOne
    private DespesaProvisionada despesaProvisionada;

    @ManyToOne
    private ReceitaProvisionada receitaProvisionada;

    @ManyToOne
    private Despesa despesa;

    @ManyToOne
    private Receita receita;

    @ManyToOne
    private Pessoa pessoa;

    @ManyToOne
    private Cambio cambio;

    @ManyToOne
    private ConhecimentoDeFrete conhecimentoDeFrete;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Titulo titulo;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Transferencia transferencia;

    @ManyToOne
    private Recepcao recepcao;

    @ManyToOne
    private NotaEmitida notaEmitida;

    private boolean cancelada = false;

    public Baixa() {
    }

    public Baixa(Long id, Integer numeroParcela, boolean cancelada,
            BigDecimal juros, BigDecimal valor, BigDecimal multas,
            BigDecimal desconto, Date emissao, String historico,
            OperacaoFinanceira tipoMovimentacaoFinanceira, Pessoa pessoa, Despesa despesa,
            Conta conta, Receita receita, Cambio cambio, Titulo titulo, Transferencia transferencia,
            Recepcao recepcao, DespesaProvisionada despesaProvisionada,
            ReceitaProvisionada receitaProvisionada, NotaEmitida notaEmitida) throws DadoInvalidoException {
        this.id = id;
        this.numeroParcela = numeroParcela;
        this.cancelada = cancelada;
        this.juros = juros;
        this.valor = valor;
        this.multas = multas;
        this.desconto = desconto;
        this.emissao = emissao;
        this.historico = historico;
        this.pessoa = pessoa;
        this.naturezaFinanceira = tipoMovimentacaoFinanceira;
        this.despesa = despesa;
        this.conta = conta;
        this.total = valor.add(juros == null ? BigDecimal.ZERO : juros).add(multas == null ? BigDecimal.ZERO : multas)
                .subtract(desconto == null ? BigDecimal.ZERO : desconto);
        this.receita = receita;
        this.cambio = cambio;
        this.titulo = titulo;
        this.transferencia = transferencia;
        this.recepcao = recepcao;
        this.despesaProvisionada = despesaProvisionada;
        this.receitaProvisionada = receitaProvisionada;
        this.notaEmitida = notaEmitida;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("conta", "historico");
        new ValidadorDeCampos<Baixa>().valida(this, campos);
    }

    public void atualizaValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void atualizaTotal(BigDecimal total) {
        this.total = total;
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

    public String toString() {
        return "BaixaTitulo"
                + " - Código: " + id
                + " - NumeroParcela: " + numeroParcela
                + " - Cancelada: " + cancelada
                + " - Juros: " + juros
                + " - ValorPagRec: " + valor
                + " - Multas: " + multas
                + " - Desconto: " + desconto
                + " - DataBaixa: " + emissao
                + " - Historico: " + historico;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public String getHistorico() {
        return historico;
    }

    public DespesaProvisionada getDespesaProvisionada() {
        return despesaProvisionada;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public ReceitaProvisionada getReceitaProvisionada() {
        return receitaProvisionada;
    }

    public String getSaldoFormatado(BigDecimal saldoAtual) {
        if (naturezaFinanceira == OperacaoFinanceira.ENTRADA) {
            return conta.getMoeda().getSigla() + " " + NumberUtils.format(saldoAtual.add(this.getValor()));
        } else if (naturezaFinanceira == OperacaoFinanceira.SAIDA) {
            return conta.getMoeda().getSigla() + " " + NumberUtils.format(saldoAtual.subtract(this.getValor()));
        } else {
            return conta.getMoeda().getSigla() + " " + NumberUtils.format(saldoAtual);
        }
    }

    public BigDecimal getSaldo(BigDecimal saldoAtual) {
        if (naturezaFinanceira == OperacaoFinanceira.ENTRADA) {
            return saldoAtual.add(this.getValor());
        } else if (naturezaFinanceira == OperacaoFinanceira.SAIDA) {
            return saldoAtual.subtract(this.getValor());
        } else {
            return saldoAtual;
        }
    }

    public String getHistoricoMovimentacao() {
        BundleUtil msg = new BundleUtil();
        if (naturezaFinanceira == OperacaoFinanceira.SAIDA) {
            if (cambio != null && despesaProvisionada == null) {
                return geraMovimentacaoSaidaCambio(msg);
            } else if (recepcao != null) {
                return geraMovimentacaoSaidaRecepcao(msg);
            } else if (transferencia != null && despesa == null) {
                return geraMovimentacaoSaidaTransferencia(msg);
            } else if (despesaProvisionada != null) {
                geraMovimentacaoDespesaProvisionada(msg);
            } else {
                return geraMovimentacaoDespesa(msg);
            }
        } else if (naturezaFinanceira == OperacaoFinanceira.ENTRADA) {
            if (recepcao != null) {
                return geraMovimentacaoEntradaRecepcao(msg);
            } else if (notaEmitida != null) {
                return geraMovimentacaoEntradaNotaEmitida(msg);
            } else if (transferencia != null) {
                return geraMovimentacaoEntradaTransferencia(msg);
            } else if (despesaProvisionada != null) {
                return geraMovimentacaoEntradaDespesaProvisionada(msg);
            } else if (receitaProvisionada != null) {
                return geraMovimentacaoReceitaProvisionada(msg);
            } else {
                return geraMovimentacaoReceita(msg);
            }
        }
        return "";
    }

    public void cancelar() throws EDadoInvalidoException {
        if (getCambio() != null) {
            throw new EDadoInvalidoException("Baixas com referências de câmbio não podem ser canceladas!");
        }
        if (titulo != null) {
            titulo.cancelarSaldoDeBaixa(valor);
        }
        this.cancelada = true;
    }

    private String geraMovimentacaoReceita(BundleUtil msg) {
        String historicoFormatado = "";
        if (historico != null) {
            historicoFormatado = historico.length() > 25 ? historico.substring(0, 24) : historico;
        }
        String str = pessoa == null ? historicoFormatado : " " + msg.getMessage("pagos_por") + " " + pessoa.getNome();
        return this.receita.getNome() + " - " + str;
    }

    private String geraMovimentacaoEntradaNotaEmitida(BundleUtil msg) {
        return msg.getMessage("Nota_Emitida") + " - " + notaEmitida.getId() + " - " + this.getPessoa();
    }

    private String geraMovimentacaoEntradaTransferencia(BundleUtil msg) {
        return msg.getMessage("Entrada_Transferencia") + " " + conta.getNome();
    }

    private String geraMovimentacaoEntradaRecepcao(BundleUtil msg) {
        return msg.getMessage("Recebimento_Recepcao")
                + " " + pessoa.getNome();
    }

    private String geraMovimentacaoDespesaProvisionada(BundleUtil msg) {
        String historicoFormatado = "";
        if (historico != null) {
            historicoFormatado = historico.length() > 25 ? historico.substring(0, 24) : historico;
        }
        return this.despesaProvisionada.getDespesa().getNome() + " - " + historicoFormatado;
    }

    private String geraMovimentacaoDespesa(BundleUtil msg) {
        String historicoFormatado = "";
        if (historico != null) {
            historicoFormatado = historico.length() > 25 ? historico.substring(0, 24) : historico;
        } else if (transferencia != null) {
            historicoFormatado = msg.getMessage("operacao_transferencia");
        }
        String str = pessoa == null ? historicoFormatado : " " + msg.getMessage("pagos_para") + " " + pessoa.getNome();
        return this.despesa.getNome() + " - " + str;
    }

    private String geraMovimentacaoSaidaTransferencia(BundleUtil msg) {
        return msg.getMessage("Saida_Transferencia") + " " + conta.getNome();
    }

    private String geraMovimentacaoEntradaDespesaProvisionada(BundleUtil msg) {
        return msg.getMessage("Entrada_Lucro") + " de câmbio " + despesaProvisionada.getCambio().getId();
    }

    private String geraMovimentacaoSaidaRecepcao(BundleUtil msg) {
        return msg.getMessage("Pagamento_Titulo")
                + " - " + pessoa.getNome();
    }

    private String geraMovimentacaoReceitaProvisionada(BundleUtil msg) {
        return msg.getMessage("Recebimento_Receita_Provisionada") + " de " + receitaProvisionada.getPessoa().getNome();
    }

    private String geraMovimentacaoSaidaCambio(BundleUtil msg) {
        if (titulo != null) {
            List<Titulo> titulos = new TituloDAO().buscarTitulos().wAPagar().ePorCambio(cambio).listaDeResultados();
            for (Titulo t : titulos) {
                if (titulo.getValor().equals(t.getValor())) {
                    return msg.getMessage("Pagamento_Comissao") + " " + cambio.getId();
                }
            }
        } else if (despesaProvisionada != null) {
            return msg.getMessage("Pagamento_Despesa_Provisionada") + " de " + despesaProvisionada.getDespesa().getNome()
                    + " para " + pessoa.getNome();
        }
        String str = despesa != null ? despesa.getNome() : historico;
        return str + " " + msg.getMessage("Cambio")
                + " " + msg.getMessage("de") + " " + cambio.getContrato().getPessoa().getNome();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format(naturezaFinanceira == OperacaoFinanceira.SAIDA ? (getValor().multiply(new BigDecimal(-1))) : getValor());
    }

    public String getValorFormatadoSemNegativos() {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format((getValor()));
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

    public String getTotalFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getTotal());
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return emissaoFormatada.format(getEmissao().getTime());
    }

    public OperacaoFinanceira getNaturezaFinanceira() {
        return naturezaFinanceira;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    
    public Conta getConta() {
        return conta;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public Receita getReceita() {
        return receita;
    }

    @Override
    public Date getVencimento() {
        return null;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    @Override
    public Date getUltimoPagamento() {
        return emissao;
    }

    public Long getDocumentoOriginal() {
        if (cambio != null) {
            return this.cambio.getId();
        } else if (recepcao != null && titulo == null) {
            return this.recepcao.getId();
        } else if (recepcao != null && titulo != null) {
            return this.titulo.getId();
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
        } else if (titulo != null) {
            return titulo.getId();
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
        } else if (titulo != null) {
            return TipoOperacao.TITULO.getNome();
        } else if (despesaProvisionada != null) {
            return despesaProvisionada.getDespesa().getNome();
        } else if (receitaProvisionada != null) {
            return receitaProvisionada.getReceita().getNome();
        } else {
            return TipoOperacao.AVULSO.getNome();
        }
    }

    @Override
    public BigDecimal getValorBaixado() {
        return titulo != null ? titulo.getValorBaixado() : getValor();
    }

}
