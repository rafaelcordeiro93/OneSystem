/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class PagamentoBuilder {

    private Long id;
    private List<TipoDeCobranca> tiposDeCobranca;
    private List<FormaDeCobranca> formasDeCobranca;
    private BigDecimal totalEmDinheiro;
    private Cotacao cotacaoPadrao;
    private Date emissao;
    private EstadoDeLancamento estado;
    private Caixa caixa;

    public PagamentoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public PagamentoBuilder comTipoDeCobranca(List<TipoDeCobranca> tiposDeCobranca) {
        this.tiposDeCobranca = tiposDeCobranca;
        return this;
    }

    public PagamentoBuilder comFormasDeCobranca(List<FormaDeCobranca> formasDeCobranca) {
        this.formasDeCobranca = formasDeCobranca;
        return this;
    }

    public PagamentoBuilder comTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
        return this;
    }

    public PagamentoBuilder comCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
        return this;
    }

    public PagamentoBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public PagamentoBuilder comEstadoDeLancamento(EstadoDeLancamento estado) {
        this.estado = estado;
        return this;
    }

    public PagamentoBuilder comCaixa(Caixa caixa) {
        this.caixa = caixa;
        return this;
    }

    public Pagamento construir() throws DadoInvalidoException {
        return new Pagamento(id, tiposDeCobranca, formasDeCobranca, cotacaoPadrao, emissao, totalEmDinheiro, estado, caixa);
    }

}
