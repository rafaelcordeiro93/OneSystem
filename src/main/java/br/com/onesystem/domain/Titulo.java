package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
public class Titulo extends Cobranca implements RelatorioContaAbertaImpl {

    @Column(nullable = false)
    private BigDecimal saldo;

    @OneToOne
    private Recepcao recepcao;

    @ManyToOne
    private Cambio cambio;

    @NotNull(message = "{tipoFormaPagRec_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoFormaPagRec tipoFormaPagRec;

    @ManyToOne
    private ConhecimentoDeFrete conhecimentoDeFrete;

    @ManyToOne
    private FaturaLegada faturaLegada;

    @ManyToOne
    private FaturaEmitida faturaEmitida;

    @ManyToOne
    private FaturaRecebida faturaRecebida;

    public Titulo() {
    }

    public Titulo(Long id, Pessoa pessoa, String historico, BigDecimal valor, BigDecimal saldo, Date emissao,
            OperacaoFinanceira operacaoFinanceira, TipoFormaPagRec tipoFormaPagRec, Date vencimento, Recepcao recepcao,
            Cambio cambio, Cotacao cotacao, Nota nota, ConhecimentoDeFrete conhecimentoDeFrete, List<Baixa> baixas, Boolean entrada, FaturaLegada faturaLegada,
            FaturaEmitida faturaEmitida, FaturaRecebida faturaRecebida) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, baixas, operacaoFinanceira, valor, vencimento, nota, entrada);
        this.saldo = saldo;
        this.tipoFormaPagRec = tipoFormaPagRec;
        this.recepcao = recepcao;
        this.cambio = cambio;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.faturaLegada = faturaLegada;
        this.faturaEmitida = faturaEmitida;
        this.faturaRecebida = faturaRecebida;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> camposTitulo = Arrays.asList("tipoFormaPagRec");
        new ValidadorDeCampos<>().valida(this, camposTitulo);
    }

    public void setFaturaLegada(FaturaLegada faturaLegada) {
        this.faturaLegada = faturaLegada;
    }

    public void setFaturaEmitida(FaturaEmitida faturaEmitida) {
        this.faturaEmitida = faturaEmitida;
    }

    public void setFaturaRecebida(FaturaRecebida faturaRecebida) {
        this.faturaRecebida = faturaRecebida;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    @Override
    public ModalidadeDeCobranca getModalidade() {
        return ModalidadeDeCobranca.TITULO;
    }

    public void cancelarSaldoDeBaixa(BigDecimal valor) {
        this.saldo = saldo.add(valor);
    }

    public void descancelarSaldoDeBaixa(BigDecimal valor) {
        this.saldo = saldo.subtract(valor);
    }

    public BigDecimal atualizaSaldo(BigDecimal valor) throws DadoInvalidoException {
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

    public TipoFormaPagRec getTipoFormaPagRec() {
        return tipoFormaPagRec;
    }

    public FaturaLegada getFaturaLegada() {
        return faturaLegada;
    }

    public FaturaEmitida getFaturaEmitida() {
        return faturaEmitida;
    }

    public FaturaRecebida getFaturaRecebida() {
        return faturaRecebida;
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

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public String getSaldoFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getSaldo());
    }

    @Override
    public String toString() {
        return "Titulo{" + getId() + ", saldo=" + saldo + ", operacaoFinanceira=" + getOperacaoFinanceira() + ", recepcao=" + (recepcao != null ? recepcao.getId() : null) + ", cambio=" + (cambio != null ? cambio.getId() : null) + ", nota=" + (getNota() != null ? getNota().getId() : null) + ", tipoFormaPagRec=" + tipoFormaPagRec + ", conhecimentoDeFrete=" + (conhecimentoDeFrete != null ? conhecimentoDeFrete.getId() : null) + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(getId());
        return hash;
    }

    public String getDetalhes() {
        return "";
    }

}
