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
    private NotaEmitida notaEmitida;
    private Cartao cartao;
    private Date emissao;
    private Date vencimento;
    private BigDecimal valor;
    private String codigoTransacao;
    private SituacaoDeCartao situacao;
    
    public BoletoDeCartaoBV(BoletoDeCartao boletoDeCartaoSelecionada) {
        this.id = boletoDeCartaoSelecionada.getId();
        this.notaEmitida = boletoDeCartaoSelecionada.getNotaEmitida();
        this.cartao = boletoDeCartaoSelecionada.getCartao();
        this.emissao = boletoDeCartaoSelecionada.getEmissao();
        this.vencimento = boletoDeCartaoSelecionada.getVencimento();
        this.valor = boletoDeCartaoSelecionada.getValor();
        this.codigoTransacao = boletoDeCartaoSelecionada.getCodigoTransacao();
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
    
    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }
    
    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
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

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public String getCodigoTransacao() {
        return codigoTransacao;
    }
    
    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }
    
    public SituacaoDeCartao getSituacao() {
        return situacao;
    }
    
    public void setSituacao(SituacaoDeCartao situacao) {
        this.situacao = situacao;
    }
    
    public BoletoDeCartao construir() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comNotaEmitida(notaEmitida).comCartao(cartao).comEmissao(emissao).comVencimento(vencimento).comValor(valor).comCodigoTransacao(codigoTransacao).comTipoSituacao(situacao).construir();
    }
    
    public BoletoDeCartao construirComID() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comID(id).comNotaEmitida(notaEmitida).comCartao(cartao).comEmissao(emissao).comVencimento(vencimento).comValor(valor).comCodigoTransacao(codigoTransacao).comTipoSituacao(situacao).construir();
    }
}
