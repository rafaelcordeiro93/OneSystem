/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.builder.*;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class PagamentoBV implements BuilderView<Pagamento> {

    private Long id;
    private List<TipoDeCobranca> tiposDeCobranca;
    private List<FormaDeCobranca> formasDeCobranca;
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;
    private Cotacao cotacaoPadrao;
    private Date emissao;

    public PagamentoBV() {
    }

    public PagamentoBV(Date emissao) {
        this.emissao = emissao;
    }

    public PagamentoBV(Pagamento r) {
        this.id = r.getId();
        this.tiposDeCobranca = r.getTipoDeCobranca();
        this.formasDeCobranca = r.getFormasDeCobranca();
        this.cotacaoPadrao = r.getCotacaoPadrao();
        this.emissao = r.getEmissao();
        this.totalEmDinheiro = r.getTotalEmDinheiro();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TipoDeCobranca> getTiposDeCobranca() {
        return tiposDeCobranca;
    }

    public void setTiposDeCobranca(List<TipoDeCobranca> tiposDeCobranca) {
        this.tiposDeCobranca = tiposDeCobranca;
    }

    public List<FormaDeCobranca> getFormasDeCobranca() {
        return formasDeCobranca;
    }

    public void setFormasDeCobranca(List<FormaDeCobranca> formasDeCobranca) {
        this.formasDeCobranca = formasDeCobranca;
    }

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public void setTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public void setCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Pagamento construir() throws DadoInvalidoException {
        return new PagamentoBuilder().comCotacaoPadrao(cotacaoPadrao).comEmissao(emissao)
                .comFormasDeCobranca(formasDeCobranca).comTipoDeCobranca(tiposDeCobranca)
                .comTotalEmDinheiro(totalEmDinheiro).construir();
    }

    @Override
    public Pagamento construirComID() throws DadoInvalidoException {
        return new PagamentoBuilder().comId(id).comCotacaoPadrao(cotacaoPadrao).comEmissao(emissao)
                .comFormasDeCobranca(formasDeCobranca).comTipoDeCobranca(tiposDeCobranca)
                .comTotalEmDinheiro(totalEmDinheiro).construir();
    }

}
