package br.com.onesystem.war.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.BoletoDeCartaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BoletoDeCartaoBV implements Serializable, BuilderView<BoletoDeCartao> {

    private Long id;
    private Nota nota;
    private Cartao cartao;
    private Date emissao;
    private Date vencimento;
    private BigDecimal valor;
    private String codigoTransacao;
    private SituacaoDeCartao situacao;
    private Cotacao cotacao;
    private Pessoa pessoa;
    private OperacaoFinanceira operacaoFinanceira;
    private Boolean entrada;

    public BoletoDeCartaoBV(BoletoDeCartao b) {
        this.id = b.getId();
        this.nota = b.getNota();
        this.cartao = b.getCartao();
        this.emissao = b.getEmissao();
        this.vencimento = b.getVencimento();
        this.valor = b.getValor();
        this.codigoTransacao = b.getCodigoTransacao();
        this.situacao = b.getSituacao();
        this.cotacao = b.getCotacao();
        this.pessoa = b.getPessoa();
        this.operacaoFinanceira = b.getOperacaoFinanceira();
    }

    public BoletoDeCartaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
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

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }
    
    public BoletoDeCartao construir() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comNota(nota).
                comCartao(cartao).comEmissao(emissao).comPessoa(pessoa).comOperacaoFinanceira(operacaoFinanceira).
                comVencimento(vencimento).comValor(valor).comCotacao(cotacao).comEntrada(entrada).
                comCodigoTransacao(codigoTransacao).comTipoSituacao(situacao).construir();
    }

    public BoletoDeCartao construirComID() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comID(id).
                comNota(nota).comCotacao(cotacao).comPessoa(pessoa).comOperacaoFinanceira(operacaoFinanceira).
                comCartao(cartao).comEmissao(emissao).comVencimento(vencimento).comEntrada(entrada).
                comValor(valor).comCodigoTransacao(codigoTransacao).comTipoSituacao(situacao).construir();
    }
}
