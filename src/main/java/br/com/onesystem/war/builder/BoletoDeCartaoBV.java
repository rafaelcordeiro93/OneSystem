package br.com.onesystem.war.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.builder.BoletoDeCartaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BoletoDeCartaoBV implements Serializable {

    private Long id;
    private NotaEmitida venda;
    private Cartao cartao;
    private Date emissao = new Date();
    private Integer dias;
    private BigDecimal valor;
    private String codTransacao;
    private SituacaoDeCartao situacao;

    public BoletoDeCartaoBV(BoletoDeCartao boletoDeCartaoSelecionada) {
        this.id = boletoDeCartaoSelecionada.getId();
        this.venda = boletoDeCartaoSelecionada.getVenda();
        this.cartao = boletoDeCartaoSelecionada.getCartao();
        this.emissao = boletoDeCartaoSelecionada.getEmissao();
        this.dias = boletoDeCartaoSelecionada.getDias();
        this.valor = boletoDeCartaoSelecionada.getValor();
        this.codTransacao = boletoDeCartaoSelecionada.getCodigoTransacao();
        this.situacao = boletoDeCartaoSelecionada.getSituacao();

    }

    public BoletoDeCartaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotaEmitida getVenda() {
        return venda;
    }

    public void setVenda(NotaEmitida venda) {
        this.venda = venda;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodTransacao() {
        return codTransacao;
    }

    public void setCodTransacao(String codTransacao) {
        this.codTransacao = codTransacao;
    }

    public SituacaoDeCartao getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoDeCartao situacao) {
        this.situacao = situacao;
    }

    public BoletoDeCartao construir() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comVenda(venda).comCartao(cartao).comEmissao(emissao).comDias(dias).comValor(valor).comCodTransacao(codTransacao).comTipoSituacao(situacao).construir();
    }

    public BoletoDeCartao construirComID() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comID(id).comVenda(venda).comCartao(cartao).comEmissao(emissao).comDias(dias).comValor(valor).comCodTransacao(codTransacao).comTipoSituacao(situacao).construir();
    }
}
