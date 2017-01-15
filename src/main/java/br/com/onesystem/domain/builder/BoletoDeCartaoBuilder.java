package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class BoletoDeCartaoBuilder {

    private Long id;
    private NotaEmitida venda;
    private Cartao cartao;
    private Date emissao;
    private Integer dias;
    private BigDecimal valor;
    private String codTransacao;
    private SituacaoDeCartao tipoSituacao;
    private Integer numeroParcela;

    public BoletoDeCartaoBuilder() {
    }

    public BoletoDeCartaoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public BoletoDeCartaoBuilder comVenda(NotaEmitida venda) {
        this.venda = venda;
        return this;
    }

    public BoletoDeCartaoBuilder comCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public BoletoDeCartaoBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public BoletoDeCartaoBuilder comDias(Integer dias) {
        this.dias = dias;
        return this;
    }

    public BoletoDeCartaoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public BoletoDeCartaoBuilder comCodTransacao(String codTransacao) {
        this.codTransacao = codTransacao;
        return this;
    }

    public BoletoDeCartaoBuilder comTipoSituacao(SituacaoDeCartao tipoSituacao) {
        this.tipoSituacao = tipoSituacao;
        return this;
    }
    
    public BoletoDeCartaoBuilder comNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
        return this;
    }

    public BoletoDeCartao construir() throws DadoInvalidoException {
        return new BoletoDeCartao(id, venda, cartao, emissao, dias, valor, codTransacao, tipoSituacao, numeroParcela);
    }

}
