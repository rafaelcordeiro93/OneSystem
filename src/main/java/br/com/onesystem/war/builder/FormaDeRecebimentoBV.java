package br.com.onesystem.war.builder;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.builder.FormaDeRecebimentoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class FormaDeRecebimentoBV implements Serializable {

    Long id;
    String nome;
    boolean ativo;
    boolean entrada;
    BigDecimal porcentagemDeEntrada;
    TipoFormaDeRecebimento formaPadraoDeEntrada;
    boolean entradaEmCartao;
    boolean entradaEmDinheiro;
    boolean entradaEmCheque;
    boolean entradaEmCredito;
    boolean parcelaEmCheque;
    boolean parcelaEmCartao;
    boolean parcelaEmConta;
    Integer minimoDeParcelas;
    Integer maximoDeParcelas;
    Integer periodicidade;
    TipoPeriodicidade tipoPeriodicidade;
    Integer diasPrimeiraParcela;

    public FormaDeRecebimentoBV(FormaDeRecebimento formaDeRecebimentoSelecionada) {
        this.id = formaDeRecebimentoSelecionada.getId();
        this.nome = formaDeRecebimentoSelecionada.getNome();
        this.ativo = formaDeRecebimentoSelecionada.isAtivo();
        this.entrada = formaDeRecebimentoSelecionada.isEntrada();
        this.porcentagemDeEntrada = formaDeRecebimentoSelecionada.getPorcentagemDeEntrada();
        this.formaPadraoDeEntrada = formaDeRecebimentoSelecionada.getFormaPadraoDeEntrada();
        this.entradaEmCartao = formaDeRecebimentoSelecionada.isEntradaEmCartao();
        this.entradaEmDinheiro = formaDeRecebimentoSelecionada.isEntradaEmDinheiro();
        this.entradaEmCheque = formaDeRecebimentoSelecionada.isEntradaEmCheque();
        this.entradaEmCredito = formaDeRecebimentoSelecionada.isEntradaEmCredito();
        this.parcelaEmCheque = formaDeRecebimentoSelecionada.isParcelaEmCheque();
        this.parcelaEmCartao = formaDeRecebimentoSelecionada.isParcelaEmCartao();
        this.parcelaEmConta = formaDeRecebimentoSelecionada.isParcelaEmConta();
        this.minimoDeParcelas = formaDeRecebimentoSelecionada.getMinimoDeParcelas();
        this.maximoDeParcelas = formaDeRecebimentoSelecionada.getMaximoDeParcelas();
        this.periodicidade = formaDeRecebimentoSelecionada.getPeriodicidade();
        this.tipoPeriodicidade = formaDeRecebimentoSelecionada.getTipoPeriodicidade();
        this.diasPrimeiraParcela = formaDeRecebimentoSelecionada.getDiasPrimeiraParcela();
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

    
    
    public FormaDeRecebimento construirComID() throws DadoInvalidoException {
        return new FormaDeRecebimentoBuilder().comID(id).comNome(nome).comAtivo(ativo).comEntrada(entrada).comPorcentagemDeEntrada(porcentagemDeEntrada).comFormaPadraEntrada(formaPadraoDeEntrada)
                .comEntradaEmCartao(entradaEmCartao).comEntradaEmCredito(entradaEmCredito).comEntradaEmCheque(entradaEmCheque).comEntradaEmDinheiro(entradaEmDinheiro).comParcelaEmCartao(parcelaEmCartao)
                .comParcelaEmCheque(parcelaEmCheque).comParcelaEmConta(parcelaEmConta).comMinimoDeParcelas(minimoDeParcelas).comMaximoDeParcelas(maximoDeParcelas).comPeriodicidade(periodicidade)
                .comTipoPeriodicidade(tipoPeriodicidade).comDiasPrimeiraParcela(diasPrimeiraParcela).construir();
    }

    public FormaDeRecebimento construir() throws DadoInvalidoException {
        return new FormaDeRecebimentoBuilder().comNome(nome).comAtivo(ativo).comEntrada(entrada).comPorcentagemDeEntrada(porcentagemDeEntrada).comFormaPadraEntrada(formaPadraoDeEntrada)
                .comEntradaEmCartao(entradaEmCartao).comEntradaEmCredito(entradaEmCredito).comEntradaEmCheque(entradaEmCheque).comEntradaEmDinheiro(entradaEmDinheiro).comParcelaEmCartao(parcelaEmCartao)
                .comParcelaEmCheque(parcelaEmCheque).comParcelaEmConta(parcelaEmConta).comMinimoDeParcelas(minimoDeParcelas).comMaximoDeParcelas(maximoDeParcelas).comPeriodicidade(periodicidade)
                .comTipoPeriodicidade(tipoPeriodicidade).comDiasPrimeiraParcela(diasPrimeiraParcela).construir();
    }

}
