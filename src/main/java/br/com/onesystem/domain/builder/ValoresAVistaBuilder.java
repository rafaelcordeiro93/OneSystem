package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ValoresAVistaBuilder {

    private Long id;
    private Cotacao cotacao;
    private BigDecimal dinheiro;
    private List<Cheque> cheques;
    private BoletoDeCartao boletoDeCartao;
    private BigDecimal aFaturar;
    private NotaEmitida notaEmitida;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;

    public ValoresAVistaBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ValoresAVistaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ValoresAVistaBuilder comDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
        return this;
    }

    public ValoresAVistaBuilder comCheques(List<Cheque> cheques) {
        this.cheques = cheques;
        return this;
    }

    public ValoresAVistaBuilder comBoletoDeCartao(BoletoDeCartao boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
        return this;
    }

    public ValoresAVistaBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public ValoresAVistaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public ValoresAVistaBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public ValoresAVistaBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public ValoresAVistaBuilder comAFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
        return this;
    }

    public ValoresAVistaBuilder comNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }

    public ValoresAVista construir() throws DadoInvalidoException {
        return new ValoresAVista(id, dinheiro, boletoDeCartao, aFaturar, notaEmitida, cotacao, desconto, acrescimo, despesaCobranca, frete, cheques);
    }

}
