package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_BOLETODECARTAO",
        sequenceName = "SEQ_BOLETODECARTAO")
public class BoletoDeCartao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_BOLETODECARTAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private NotaEmitida notaEmitida;
    @NotNull(message = "{cartao_not_null}")
    @ManyToOne
    private Cartao cartao;
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();
    @NotNull(message = "{dias_not_null}")
    private Integer dias;
    @NotNull(message = "{valor_not_null}")
    private BigDecimal valor;
    @CharacterType(value = CaseType.DIGIT, message = "cod_transacao_somente_numeros")
    @NotNull(message = "{cod_transacao_not_null}")
    private String codigoTransacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_situacao_not_null}")
    private SituacaoDeCartao situacao;
    @NotNull(message = "{numero_parcelas_not_null}")
    private Integer numeroParcela;
    @OneToOne
    private ValoresAVista valoresAVista;

    public BoletoDeCartao() {
    }

    public BoletoDeCartao(Long id, NotaEmitida notaEmitida, Cartao cartao, Date emissao, Integer dias, BigDecimal valor, String codigoTransacao, SituacaoDeCartao situacao,
            Integer numeroParcela, ValoresAVista formaDeRecebimentoOuPagamento) throws DadoInvalidoException {
        this.id = id;
        this.notaEmitida = notaEmitida;
        this.cartao = cartao;
        this.emissao = emissao;
        this.dias = dias;
        this.valor = valor;
        this.codigoTransacao = codigoTransacao;
        this.situacao = situacao;
        this.numeroParcela = numeroParcela;
        this.valoresAVista = formaDeRecebimentoOuPagamento;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("emissao", "dias", "valor", "codigoTransacao", "numeroParcela");
        new ValidadorDeCampos<BoletoDeCartao>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Integer getDias() {
        return dias;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public SituacaoDeCartao getSituacao() {
        return situacao;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public ValoresAVista getValoresAVista() {
        return valoresAVista;
    }
        
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof BoletoDeCartao)) {
            return false;
        }
        BoletoDeCartao outro = (BoletoDeCartao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "BoletoDeCartao{" + "id=" + id + ", venda=" + (notaEmitida == null ? null : notaEmitida.getId()) + ", cartao=" + (cartao == null ? null : cartao.getId()) + ", emissao=" + emissao + ", dias=" + dias + ", valor=" + valor + ", codTransacao=" + codigoTransacao + ", situacao=" + situacao + ", numeroParcela=" + numeroParcela + ", formaDeRecebimentoOuPagamento=" + valoresAVista + '}';
    }

}
