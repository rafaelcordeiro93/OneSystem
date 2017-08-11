package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("CHEQUE")
public class Cheque extends Cobranca implements Serializable {

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
    private DepositoBancario depositoBancario;

    public Cheque() {
    }

    public Cheque(Long id, Nota nota, BigDecimal valor, Date emissao, Date vencimento, Banco banco, String agencia,
            String conta, String numeroCheque, SituacaoDeCheque tipoSituacao, BigDecimal multas, BigDecimal juros, BigDecimal descontos, String emitente, OperacaoFinanceira operacaoFinanceira,
            String historico, Cotacao cotacao, TipoLancamento tipoLancamento, Pessoa pessoa, List<Baixa> baixas, Boolean entrada, SituacaoDeCobranca situacaoDeCobranca) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, baixas, operacaoFinanceira, valor, vencimento, nota, entrada, situacaoDeCobranca);
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.tipoSituacao = tipoSituacao;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.tipoLancamento = tipoLancamento;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> camposCheque = Arrays.asList("banco", "agencia", "conta", "numeroCheque", "tipoSituacao",
                "multas", "juros", "descontos", "emitente");
        new ValidadorDeCampos<Cheque>().valida(this, camposCheque);
    }

    @Override
    public ModalidadeDeCobranca getModalidade() {
        return ModalidadeDeCobranca.CHEQUE;
    }

    public void cancela() {
        this.tipoSituacao = SituacaoDeCheque.CANCELADO;
    }

    public void devolve() {
        this.tipoSituacao = SituacaoDeCheque.DEVOLVIDO;
    }

    public void desconta() {
        this.tipoSituacao = SituacaoDeCheque.DESCONTADO;
    }

    public void depositaNo(DepositoBancario deposito) {
        this.depositoBancario = deposito;
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

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public String getDetalhes() {
        return banco == null ? "" : banco.getNome() + " / " + agencia + " / " + conta + " Nº " + numeroCheque;
    }

    @Override
    public String toString() {
        return "Cheque{" + "id=" + getId() + ", nota=" + (getNota() == null ? null : getNota().getId()) + ", valor=" + valor
                + ", emissao=" + getEmissao() + ", vencimento=" + getVencimento() + ", banco=" + (banco == null ? null : banco.getId()) + ", agencia=" + agencia
                + ", conta=" + conta + ", numeroCheque=" + numeroCheque + ", tipoSituacao=" + tipoSituacao + ", multas=" + multas + ", juros=" + juros
                + ", tipoLancamento=" + tipoLancamento + ", descontos=" + descontos + ", emitente=" + emitente + ", historico=" + getHistorico()
                + '}';
    }

}
