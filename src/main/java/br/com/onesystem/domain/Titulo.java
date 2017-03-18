package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("TITULO")
public class Titulo extends PerfilDeValor implements RelatorioContaAbertaImpl {

    @Column(nullable = false)
    private BigDecimal saldo;

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira unidadeFinanceira;

    @OneToOne
    private Recepcao recepcao;

    @ManyToOne
    private Cambio cambio;

    @ManyToOne
    private NotaEmitida notaEmitida;

    @NotNull(message = "{tipoFormaPagRec_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoFormaPagRec tipoFormaPagRec;

    @ManyToOne
    private ConhecimentoDeFrete conhecimentoDeFrete;

    public Titulo() {
    }

    public Titulo(Long id, Pessoa pessoa, String historico, BigDecimal valor, BigDecimal saldo, Date emissao,
            OperacaoFinanceira unidadeFinanceira, TipoFormaPagRec tipoFormaPagRec, Date vencimento, Recepcao recepcao,
            Cambio cambio, Cotacao cotacao, NotaEmitida notaEmitida, ConhecimentoDeFrete conhecimentoDeFrete, List<Baixa> baixas) throws DadoInvalidoException {
        super(id, valor, vencimento, emissao, pessoa, cotacao, historico, baixas);
        this.unidadeFinanceira = unidadeFinanceira;
        this.saldo = saldo;
        this.tipoFormaPagRec = tipoFormaPagRec;
        this.recepcao = recepcao;
        this.cambio = cambio;
        this.notaEmitida = notaEmitida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "historico", "valor", "cotacao");
        List<String> camposTitulo = Arrays.asList("unidadeFinanceira");
        new ValidadorDeCampos<PerfilDeValor>().valida(this, campos);
        new ValidadorDeCampos<Titulo>().valida(this, camposTitulo);
    }

    public void cancelarSaldoDeBaixa(BigDecimal valor) {
        this.saldo = saldo.add(valor);
    }

    public BigDecimal atualizaSaldo(BigDecimal valor) throws EDadoInvalidoException {
        if (valor.compareTo(saldo) == 1) {
            throw new EDadoInvalidoException("O valor deve ser menor ou igual ao saldo!");
        } else {
            this.saldo = saldo.subtract(valor);
        }
        return valor;
    }

    public void setValor(BigDecimal valor) throws EDadoInvalidoException {
        if (saldo.compareTo(getValor()) == 0) {
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

    public Cambio getCambio() {
        return cambio;
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

    @Override
    public String getOrigem() {
        return cambio != null ? TipoOperacao.CAMBIO.getNome() : TipoOperacao.RECEPCAO.getNome();
    }

    public Moeda getMoeda() {
        return getCotacao().getConta().getMoeda();
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public String getSaldoFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getSaldo());
    }

    @Override
    public String toString() {
        return "Titulo{" + "saldo=" + saldo + ", unidadeFinanceira=" + unidadeFinanceira + ", recepcao=" + (recepcao != null ? recepcao.getId() : null) + ", cambio=" + (cambio != null ? cambio.getId() : null) + ", notaEmitida=" + (notaEmitida != null ? notaEmitida.getId() : null) + ", tipoFormaPagRec=" + tipoFormaPagRec + ", conhecimentoDeFrete=" +  (conhecimentoDeFrete != null ? conhecimentoDeFrete.getId() : null) + '}';
    }
    
}
