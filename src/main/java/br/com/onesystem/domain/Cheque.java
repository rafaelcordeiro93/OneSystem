package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("CHEQUE")
public class Cheque extends FormaPagamentoRecebimento implements Serializable {

    @ManyToOne
    private NotaEmitida notaEmitida;
    @NotNull(message = "{banco_not_null}")
    @ManyToOne
    private Banco banco;
    @NotNull(message = "{agencia_not_null}")
    private String agencia;
    @NotNull(message = "{conta_not_null}")
    private String conta;
    @NotNull(message = "{numero_cheque_not_null}")
    private String numeroCheque;
    @NotNull(message = "{tipo_situacao_not_null}")
    @Enumerated(EnumType.STRING)
    private SituacaoDeCheque tipoSituacao;
    @NotNull(message = "{tipo_lancamento_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento;
    @Column(nullable = true)
    private BigDecimal multas;
    @Column(nullable = true)
    private BigDecimal juros;
    @Column(nullable = true)
    private BigDecimal descontos;
    @Column(nullable = true)
    private String emitente;
    @ManyToOne
    private ValoresAVista valoresAVista;

    public Cheque() {
    }

    public Cheque(Long id, NotaEmitida notaEmitida, BigDecimal valor, Date emissao, Date vencimento, Banco banco, String agencia,
            String conta, String numeroCheque, SituacaoDeCheque tipoSituacao, BigDecimal multas, BigDecimal juros, BigDecimal descontos, String emitente,
            String historico, ValoresAVista valoresAVista, Cotacao cotacao, TipoLancamento tipoLancamento) throws DadoInvalidoException {
        super(id, emissao, valor, BigDecimal.ZERO, BigDecimal.ZERO, historico, vencimento, cotacao);
        this.notaEmitida = notaEmitida;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.tipoSituacao = tipoSituacao;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.valoresAVista = valoresAVista;
        this.tipoLancamento = tipoLancamento;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "vencimento", "historico", "cotacao");
        List<String> camposCheque = Arrays.asList("banco", "agencia", "conta", "numeroCheque", "tipoSituacao",
                "multas", "juros", "descontos", "emitente");
        new ValidadorDeCampos<Cheque>().valida(this, camposCheque);
        new ValidadorDeCampos<FormaPagamentoRecebimento>().valida(this, campos);
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public String getValorFormatado() {
        Locale local = getCotacao().getConta().getMoeda().getBandeira().getLocal();
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);

        return nf.format(getValor());
    }

    public Banco getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public SituacaoDeCheque getTipoSituacao() {
        return tipoSituacao;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public BigDecimal getDescontos() {
        return descontos;
    }

    public String getEmitente() {
        return emitente;
    }

    public ValoresAVista getValoresAVista() {
        return valoresAVista;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    @Override
    public String toString() {
        return "Cheque{" + "id=" + getId() + ", venda=" + (notaEmitida == null ? null : notaEmitida.getId()) + ", valor=" + valor
                + ", emissao=" + getEmissao() + ", vencimento=" + getVencimento() + ", banco=" + (banco == null ? null : banco.getId()) + ", agencia=" + agencia
                + ", conta=" + conta + ", numeroCheque=" + numeroCheque + ", tipoSituacao=" + tipoSituacao + ", multas=" + multas + ", juros=" + juros
                + ", descontos=" + descontos + ", emitente=" + emitente + ", historico=" + getHistorico() + ", formaDeRecebimentoOuPagamento=" + valoresAVista + '}';
    }

}
