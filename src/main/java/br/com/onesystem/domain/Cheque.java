package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CHEQUE",
        sequenceName = "SEQ_CHEQUE")
public class Cheque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CHEQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private NotaEmitida venda;
    @NotNull(message = "{numero_parcelas_not_null}")
    private Integer numeroParcela;
    @NotNull(message = "{valor_not_null}")
    private BigDecimal valor;
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();
    @NotNull(message = "{vencimento_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento = new Date();
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
    private SituacaoDeCheque tipoSituacao;
    @Column(nullable = true)
    private BigDecimal multas;
    @Column(nullable = true)
    private BigDecimal juros;
    @Column(nullable = true)
    private BigDecimal descontos;
    @Column(nullable = true)
    private String emitente;
    @Length(min = 0, max = 120, message = "{observacao_lenght}")
    @Column(length = 120, nullable = true)
    private String observacao;

    public Cheque() {
    }

    public Cheque(Long id, NotaEmitida venda, Integer numeroParcela, BigDecimal valor, Date emissao, Date vencimento, Banco banco, String agencia,
            String conta, String numeroCheque, SituacaoDeCheque tipoSituacao, BigDecimal multas, BigDecimal juros, BigDecimal descontos, String emitente,
            String observacao) throws DadoInvalidoException {
        this.id = id;
        this.venda = venda;
        this.numeroParcela = numeroParcela;
        this.valor = valor;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.tipoSituacao = tipoSituacao;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.observacao = observacao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("numeroParcela", "valor", "emissao", "vencimento", "banco", "agencia", "conta", "numeroCheque", "tipoSituacao",
                "multas", "juros", "descontos", "emitente", "observacao");
        new ValidadorDeCampos<Cheque>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public NotaEmitida getVenda() {
        return venda;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Date getVencimento() {
        return vencimento;
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

    public String getObservacao() {
        return observacao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cheque)) {
            return false;
        }
        Cheque outro = (Cheque) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cheque{" + "id=" + id + ", venda=" + (venda == null ? null : venda.getId()) + ", numeroParcela=" + numeroParcela + ", valor=" + valor
                + ", emissao=" + emissao + ", vencimento=" + vencimento + ", banco=" + (banco == null ? null : banco.getId()) + ", agencia=" + agencia
                + ", conta=" + conta + ", numeroCheque=" + numeroCheque + ", tipoSituacao=" + tipoSituacao + ", multas=" + multas + ", juros=" + juros
                + ", descontos=" + descontos + ", emitente=" + emitente + ", observacao=" + observacao + '}';
    }

}
