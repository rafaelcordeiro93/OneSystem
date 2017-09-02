package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.builder.FormaDeRecebimentoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class FormaDeRecebimentoBV implements Serializable, BuilderView<FormaDeRecebimento> {
    
    private Long id;
    private String nome;
    private boolean ativo = true;
    private boolean entrada;
    private BigDecimal porcentagemDeEntrada;
    private TipoFormaDeRecebimento formaPadraoDeEntrada;
    private boolean entradaEmCartao;
    private boolean entradaEmDinheiro;
    private boolean entradaEmCheque;
    private boolean entradaEmCredito;
    private boolean parcelaEmCheque;
    private boolean parcelaEmCartao;
    private boolean parcelaEmConta;
    private Integer minimoDeParcelas;
    private Integer maximoDeParcelas;
    private Integer periodicidade;
    private TipoPeriodicidade tipoPeriodicidade;
    private Integer diasPrimeiraParcela;
    private ModalidadeDeCobranca formaPadraoDeRecebimentoParcela;
    private Cartao cartao;
    private Conta conta;
    
    public FormaDeRecebimentoBV(FormaDeRecebimento f) {
        this.id = f.getId();
        this.nome = f.getNome();
        this.ativo = f.isAtivo();
        this.entrada = f.isEntrada();
        this.porcentagemDeEntrada = f.getPorcentagemDeEntrada();
        this.formaPadraoDeEntrada = f.getFormaPadraoDeEntrada();
        this.entradaEmCartao = f.isEntradaEmCartao();
        this.entradaEmDinheiro = f.isEntradaEmDinheiro();
        this.entradaEmCheque = f.isEntradaEmCheque();
        this.entradaEmCredito = f.isEntradaEmCredito();
        this.parcelaEmCheque = f.isParcelaEmCheque();
        this.parcelaEmCartao = f.isParcelaEmCartao();
        this.parcelaEmConta = f.isParcelaEmConta();
        this.minimoDeParcelas = f.getMinimoDeParcelas();
        this.maximoDeParcelas = f.getMaximoDeParcelas();
        this.periodicidade = f.getPeriodicidade();
        this.tipoPeriodicidade = f.getTipoPeriodicidade();
        this.diasPrimeiraParcela = f.getDiasPrimeiraParcela();
        this.formaPadraoDeRecebimentoParcela = f.getFormaPadraoDeParcela();
        this.cartao = f.getCartao();
        this.conta = f.getConta();
    }
    
    public FormaDeRecebimentoBV() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public boolean isEntrada() {
        return entrada;
    }
    
    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }
    
    public BigDecimal getPorcentagemDeEntrada() {
        return porcentagemDeEntrada;
    }
    
    public void setPorcentagemDeEntrada(BigDecimal porcentagemDeEntrada) {
        this.porcentagemDeEntrada = porcentagemDeEntrada;
    }
    
    public TipoFormaDeRecebimento getFormaPadraoDeEntrada() {
        return formaPadraoDeEntrada;
    }
    
    public void setFormaPadraoDeEntrada(TipoFormaDeRecebimento formaPadraoDeEntrada) {
        this.formaPadraoDeEntrada = formaPadraoDeEntrada;
    }
    
    public boolean isEntradaEmCartao() {
        return entradaEmCartao;
    }
    
    public void setEntradaEmCartao(boolean entradaEmCartao) {
        this.entradaEmCartao = entradaEmCartao;
    }
    
    public boolean isEntradaEmDinheiro() {
        return entradaEmDinheiro;
    }
    
    public void setEntradaEmDinheiro(boolean entradaEmDinheiro) {
        this.entradaEmDinheiro = entradaEmDinheiro;
    }
    
    public boolean isEntradaEmCheque() {
        return entradaEmCheque;
    }
    
    public void setEntradaEmCheque(boolean entradaEmCheque) {
        this.entradaEmCheque = entradaEmCheque;
    }
    
    public boolean isEntradaEmCredito() {
        return entradaEmCredito;
    }
    
    public void setEntradaEmCredito(boolean entradaEmCredito) {
        this.entradaEmCredito = entradaEmCredito;
    }
    
    public boolean isParcelaEmCheque() {
        return parcelaEmCheque;
    }
    
    public void setParcelaEmCheque(boolean parcelaEmCheque) {
        this.parcelaEmCheque = parcelaEmCheque;
    }
    
    public boolean isParcelaEmCartao() {
        return parcelaEmCartao;
    }
    
    public void setParcelaEmCartao(boolean parcelaEmCartao) {
        this.parcelaEmCartao = parcelaEmCartao;
    }
    
    public boolean isParcelaEmConta() {
        return parcelaEmConta;
    }
    
    public void setParcelaEmConta(boolean parcelaEmConta) {
        this.parcelaEmConta = parcelaEmConta;
    }
    
    public Integer getMinimoDeParcelas() {
        return minimoDeParcelas;
    }
    
    public void setMinimoDeParcelas(Integer minimoDeParcelas) {
        this.minimoDeParcelas = minimoDeParcelas;
    }
    
    public Integer getMaximoDeParcelas() {
        return maximoDeParcelas;
    }
    
    public void setMaximoDeParcelas(Integer maximoDeParcelas) {
        this.maximoDeParcelas = maximoDeParcelas;
    }
    
    public Integer getPeriodicidade() {
        return periodicidade;
    }
    
    public void setPeriodicidade(Integer periodicidade) {
        this.periodicidade = periodicidade;
    }
    
    public TipoPeriodicidade getTipoPeriodicidade() {
        return tipoPeriodicidade;
    }
    
    public void setTipoPeriodicidade(TipoPeriodicidade tipoPeriodicidade) {
        this.tipoPeriodicidade = tipoPeriodicidade;
    }
    
    public Integer getDiasPrimeiraParcela() {
        return diasPrimeiraParcela;
    }
    
    public void setDiasPrimeiraParcela(Integer diasPrimeiraParcela) {
        this.diasPrimeiraParcela = diasPrimeiraParcela;
    }
    
    public ModalidadeDeCobranca getFormaPadraoDeRecebimentoParcela() {
        return formaPadraoDeRecebimentoParcela;
    }
    
    public void setFormaPadraoDeRecebimentoParcela(ModalidadeDeCobranca formaPadraoDeRecebimentoParcela) {
        this.formaPadraoDeRecebimentoParcela = formaPadraoDeRecebimentoParcela;
    }
    
    public Cartao getCartao() {
        return cartao;
    }
    
    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    public FormaDeRecebimento construirComID() throws DadoInvalidoException {
        return new FormaDeRecebimentoBuilder().comID(id).comNome(nome).comAtivo(ativo).comEntrada(entrada).comPorcentagemDeEntrada(porcentagemDeEntrada).comFormaPadraoEntrada(formaPadraoDeEntrada)
                .comEntradaEmCartao(entradaEmCartao).comEntradaEmCredito(entradaEmCredito).comEntradaEmCheque(entradaEmCheque).comEntradaEmDinheiro(entradaEmDinheiro).comParcelaEmCartao(parcelaEmCartao)
                .comParcelaEmCheque(parcelaEmCheque).comParcelaEmConta(parcelaEmConta).comMinimoDeParcelas(minimoDeParcelas).comMaximoDeParcelas(maximoDeParcelas).comPeriodicidade(periodicidade)
                .comTipoPeriodicidade(tipoPeriodicidade).comDiasPrimeiraParcela(diasPrimeiraParcela).comCartao(cartao).comConta(conta)
                .comFormaPadraoParcela(formaPadraoDeRecebimentoParcela).construir();
    }
    
    public FormaDeRecebimento construir() throws DadoInvalidoException {
        return new FormaDeRecebimentoBuilder().comNome(nome).comAtivo(ativo).comEntrada(entrada).comPorcentagemDeEntrada(porcentagemDeEntrada).comFormaPadraoEntrada(formaPadraoDeEntrada)
                .comEntradaEmCartao(entradaEmCartao).comEntradaEmCredito(entradaEmCredito).comEntradaEmCheque(entradaEmCheque).comEntradaEmDinheiro(entradaEmDinheiro).comParcelaEmCartao(parcelaEmCartao)
                .comParcelaEmCheque(parcelaEmCheque).comParcelaEmConta(parcelaEmConta).comMinimoDeParcelas(minimoDeParcelas).comMaximoDeParcelas(maximoDeParcelas).comPeriodicidade(periodicidade)
                .comTipoPeriodicidade(tipoPeriodicidade).comDiasPrimeiraParcela(diasPrimeiraParcela).comCartao(cartao).comConta(conta)
                .comFormaPadraoParcela(formaPadraoDeRecebimentoParcela).construir();
    }
    
}
