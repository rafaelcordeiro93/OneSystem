/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Recebimento;
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
public class RecebimentoBuilder {

    private Long id;
    private List<TipoDeCobranca> tiposDeCobranca;
    private List<FormaDeCobranca> formasDeCobranca;
    private BigDecimal totalEmDinheiro;
    private Cotacao cotacaoPadrao;
    private Date emissao;
    private EstadoDeLancamento estado;
    private Caixa caixa;

    public RecebimentoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public RecebimentoBuilder comTipoDeCobranca(List<TipoDeCobranca> tiposDeCobranca) {
        this.tiposDeCobranca = tiposDeCobranca;
        return this;
    }

    public RecebimentoBuilder comFormasDeCobranca(List<FormaDeCobranca> formasDeCobranca) {
        this.formasDeCobranca = formasDeCobranca;
        return this;
    }

    public RecebimentoBuilder comTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
        return this;
    }

    public RecebimentoBuilder comCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
        return this;
    }

    public RecebimentoBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public RecebimentoBuilder comEstadoDeLancamento(EstadoDeLancamento estado) {
        this.estado = estado;

    public RecebimentoBuilder comCaixa(Caixa caixa) {
        this.caixa = caixa;
        return this;
    }

    public Recebimento construir() throws DadoInvalidoException {
        return new Recebimento(id, tiposDeCobranca, formasDeCobranca, cotacaoPadrao, emissao, totalEmDinheiro, estado);
        return new Recebimento(id, tiposDeCobranca, formasDeCobranca, cotacaoPadrao, emissao, totalEmDinheiro, caixa);
    }

}
