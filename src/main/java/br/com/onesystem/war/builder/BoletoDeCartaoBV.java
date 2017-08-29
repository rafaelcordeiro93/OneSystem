package br.com.onesystem.war.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.BoletoDeCartaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
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
    private SituacaoDeCobranca situacaoDeCobranca;
    private Filial filial;

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
        this.entrada = b.getEntrada();
        this.situacaoDeCobranca = b.getSituacaoDeCobranca();
        this.filial = b.getFilial();
    }

    public BoletoDeCartaoBV(CobrancaVariavel c) {
        this.id = c.getId();
        this.nota = c.getNota();
        this.cartao = ((BoletoDeCartao) c).getCartao();
        this.emissao = c.getEmissao();
        this.vencimento = c.getVencimento();
        this.valor = c.getValor();
        this.codigoTransacao = ((BoletoDeCartao) c).getCodigoTransacao();
        this.situacao = ((BoletoDeCartao) c).getSituacao();
        this.cotacao = c.getCotacao();
        this.pessoa = c.getPessoa();
        this.operacaoFinanceira = c.getOperacaoFinanceira();
        this.entrada = c.getEntrada();
        this.situacaoDeCobranca = c.getSituacaoDeCobranca();
        this.filial = c.getFilial();
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

    public SituacaoDeCobranca getSituacaoDeCobranca() {
        return situacaoDeCobranca;
    }

    public void setSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
    
    public BoletoDeCartao construir() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comNota(nota).comFilial(filial).
                comCartao(cartao).comEmissao(emissao).comPessoa(pessoa).comOperacaoFinanceira(operacaoFinanceira).
                comVencimento(vencimento).comValor(valor).comCotacao(cotacao).comEntrada(entrada).
                comCodigoTransacao(codigoTransacao).comTipoSituacao(situacao).comSituacaoDeCobranca(situacaoDeCobranca).construir();
    }

    public BoletoDeCartao construirComID() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comID(id).comFilial(filial).
                comNota(nota).comCotacao(cotacao).comPessoa(pessoa).comOperacaoFinanceira(operacaoFinanceira).
                comCartao(cartao).comEmissao(emissao).comVencimento(vencimento).comEntrada(entrada).
                comValor(valor).comCodigoTransacao(codigoTransacao).comTipoSituacao(situacao).comSituacaoDeCobranca(situacaoDeCobranca).construir();
    }
}
