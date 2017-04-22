package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("BOLETO_DE_CARTAO")
public class BoletoDeCartao extends Transacao implements Serializable {

    @ManyToOne
    private NotaEmitida notaEmitida;
    @NotNull(message = "{cartao_not_null}")
    @ManyToOne
    private Cartao cartao;
    @CharacterType(value = CaseType.DIGIT, message = "cod_transacao_somente_numeros")
    private String codigoTransacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipo_situacao_not_null}")
    private SituacaoDeCartao situacao;

    public BoletoDeCartao() {
    }

    public BoletoDeCartao(Long id, NotaEmitida notaEmitida, Cartao cartao, Date emissao, BigDecimal valor, String codigoTransacao, SituacaoDeCartao situacao,
            String historico, Date vencimento, Cotacao cotacao, Pessoa pessoa, List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, baixas, operacaoFinanceira, valor, vencimento);
        this.notaEmitida = notaEmitida;
        this.cartao = cartao;
        this.codigoTransacao = codigoTransacao;
        this.situacao = situacao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> camposBoleto = Arrays.asList("codigoTransacao", "situacao");
        new ValidadorDeCampos<BoletoDeCartao>().valida(this, camposBoleto);
        List<String> campos = Arrays.asList("valor", "emissao", "historico", "valor", "cotacao");
        new ValidadorDeCampos<Transacao>().valida(this, campos);
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

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    @Override
    public String toString() {
        return "BoletoDeCartao{" + "id=" + getId() + ", venda=" + (notaEmitida == null ? null : notaEmitida.getId())
                + ", cartao=" + (cartao == null ? null : cartao.getId()) + ", emissao=" + getEmissao()
                + ", vencimento=" + getVencimento() + ", valor=" + valor + ", codTransacao=" + codigoTransacao + ", desconto="
                + ", situacao=" + situacao + ", cotacao=" + getCotacao() + '}';
    }

}
