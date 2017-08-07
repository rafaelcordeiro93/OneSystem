package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class FormaDeRecebimentoBuilder {

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
    private ModalidadeDeCobranca formaPadraoDeRecebimentoParcela;
    private Cartao cartao;

    public FormaDeRecebimentoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FormaDeRecebimentoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public FormaDeRecebimentoBuilder comAtivo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public FormaDeRecebimentoBuilder comEntrada(Boolean entrada) {
        this.entrada = entrada;
        return this;
    }

    public FormaDeRecebimentoBuilder comPorcentagemDeEntrada(BigDecimal porcentagemDeEntrada) {
        this.porcentagemDeEntrada = porcentagemDeEntrada;
        return this;
    }

    public FormaDeRecebimentoBuilder comFormaPadraoEntrada(TipoFormaDeRecebimento formaPadraoDeEntrada) {
        this.formaPadraoDeEntrada = formaPadraoDeEntrada;
        return this;
    }

    public FormaDeRecebimentoBuilder comFormaPadraoParcela(ModalidadeDeCobranca formaPadraoDeRecebimentoParcela) {
        this.formaPadraoDeRecebimentoParcela = formaPadraoDeRecebimentoParcela;
        return this;
    }

    public FormaDeRecebimentoBuilder comEntradaEmCartao(Boolean entradaEmCartao) {
        this.entradaEmCartao = entradaEmCartao;
        return this;
    }

    public FormaDeRecebimentoBuilder comEntradaEmDinheiro(Boolean entradaEmDinheiro) {
        this.entradaEmDinheiro = entradaEmDinheiro;
        return this;
    }

    public FormaDeRecebimentoBuilder comEntradaEmCheque(Boolean entradaEmCheque) {
        this.entradaEmCheque = entradaEmCheque;
        return this;
    }

    public FormaDeRecebimentoBuilder comEntradaEmCredito(Boolean entradaEmCredito) {
        this.entradaEmCredito = entradaEmCredito;
        return this;
    }

    public FormaDeRecebimentoBuilder comParcelaEmCheque(Boolean parcelaEmCheque) {
        this.parcelaEmCheque = parcelaEmCheque;
        return this;
    }

    public FormaDeRecebimentoBuilder comParcelaEmCartao(Boolean parcelaEmCartao) {
        this.parcelaEmCartao = parcelaEmCartao;
        return this;
    }

    public FormaDeRecebimentoBuilder comParcelaEmConta(Boolean parcelaEmConta) {
        this.parcelaEmConta = parcelaEmConta;
        return this;
    }

    public FormaDeRecebimentoBuilder comMinimoDeParcelas(Integer minimoDeParcelas) {
        this.minimoDeParcelas = minimoDeParcelas;
        return this;
    }

    public FormaDeRecebimentoBuilder comMaximoDeParcelas(Integer maximoDeParcelas) {
        this.maximoDeParcelas = maximoDeParcelas;
        return this;
    }

    public FormaDeRecebimentoBuilder comPeriodicidade(Integer periodicidade) {
        this.periodicidade = periodicidade;
        return this;
    }

    public FormaDeRecebimentoBuilder comTipoPeriodicidade(TipoPeriodicidade tipoPeriodicidade) {
        this.tipoPeriodicidade = tipoPeriodicidade;
        return this;
    }

    public FormaDeRecebimentoBuilder comDiasPrimeiraParcela(Integer diasPrimeiraParcela) {
        this.diasPrimeiraParcela = diasPrimeiraParcela;
        return this;
    }

    public FormaDeRecebimentoBuilder comCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public FormaDeRecebimento construir() throws DadoInvalidoException {
        return new FormaDeRecebimento(id, nome, ativo, entrada, porcentagemDeEntrada,
                formaPadraoDeEntrada, entradaEmCartao, entradaEmDinheiro,
                entradaEmCheque, entradaEmCredito, parcelaEmCheque, parcelaEmCartao,
                parcelaEmConta, minimoDeParcelas, maximoDeParcelas, periodicidade,
                tipoPeriodicidade, diasPrimeiraParcela, formaPadraoDeRecebimentoParcela, cartao);
    }

}
