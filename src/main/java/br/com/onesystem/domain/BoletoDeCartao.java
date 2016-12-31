package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.TipoSituacao;
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
    private NotaEmitida venda;
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
    private String codTransacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_situacao_not_null}")
    private TipoSituacao tipoSituacao;

    public BoletoDeCartao() {
    }

    public BoletoDeCartao(Long id, NotaEmitida venda, Cartao cartao, Date emissao, Integer dias, BigDecimal valor, String codTransacao, TipoSituacao tipoSituacao) throws DadoInvalidoException {
        this.id = id;
        this.venda = venda;
        this.cartao = cartao;
        this.emissao = emissao;
        this.dias = dias;
        this.valor = valor;
        this.codTransacao = codTransacao;
        this.tipoSituacao = tipoSituacao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("emissao", "dias", "valor", "codTransacao");
        new ValidadorDeCampos<BoletoDeCartao>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public NotaEmitida getVenda() {
        return venda;
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

    public String getCodTransacao() {
        return codTransacao;
    }

    public TipoSituacao getTipoSituacao() {
        return tipoSituacao;
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
        return "BoletoDeCartao{" + "id=" + id + ", venda=" + (venda == null ? null : venda.getId()) + ", cartao=" + (cartao == null ? null : cartao.getId()) + ", emissao=" + emissao + ", dias=" + dias + ", valor=" + valor + ", codTransacao=" + codTransacao + ", tipoSituacao=" + tipoSituacao + '}';
    }

}
