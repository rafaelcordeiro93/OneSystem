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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("BOLETO_DE_CARTAO")
public class BoletoDeCartao extends FormaPagamentoRecebimento implements Serializable {

    @ManyToOne
    private NotaEmitida notaEmitida;
    @NotNull(message = "{cartao_not_null}")
    @ManyToOne
    private Cartao cartao;
    @CharacterType(value = CaseType.DIGIT, message = "cod_transacao_somente_numeros")
    @NotNull(message = "{cod_transacao_not_null}")
    private String codigoTransacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_situacao_not_null}")
    private SituacaoDeCartao situacao;

    public BoletoDeCartao() {
    }

    public BoletoDeCartao(Long id, NotaEmitida notaEmitida, Cartao cartao, Date emissao, BigDecimal valor, String codigoTransacao, SituacaoDeCartao situacao,
            ValoresAVista formaDeRecebimentoOuPagamento, String historico, Date vencimento, Cotacao cotacao) throws DadoInvalidoException {
        super(id, emissao, valor, BigDecimal.ZERO, BigDecimal.ZERO, historico, vencimento, cotacao);
        this.notaEmitida = notaEmitida;
        this.cartao = cartao;
        this.valor = valor;
        this.codigoTransacao = codigoTransacao;
        this.situacao = situacao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("emissao", "valor");
        List<String> camposBoleto = Arrays.asList("codigoTransacao", "situacao");
        new ValidadorDeCampos<FormaPagamentoRecebimento>().valida(this, campos);
        new ValidadorDeCampos<BoletoDeCartao>().valida(this, camposBoleto);
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public SituacaoDeCartao getSituacao() {
        return situacao;
    }

    @Override
    public String toString() {
        return "BoletoDeCartao{" + "id=" + getId() + ", venda=" + (notaEmitida == null ? null : notaEmitida.getId()) + ", cartao=" + (cartao == null ? null : cartao.getId()) + ", emissao=" + getEmissao() + ", vencimento=" + getVencimento() + ", valor=" + valor + ", codTransacao=" + codigoTransacao + ", situacao=" + situacao + '}';
    }

}
