/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ItemService;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CalculadoraDePreco {

    private Item item;
    private BigDecimal markup;
    private BigDecimal margemContribuicao;
    private BigDecimal precoMarkup;
    private BigDecimal precoMargemContribuicao;
    private final TipoDeCalculoDeCusto tipoDeCalculoDeCusto;

    public CalculadoraDePreco(Item item, TipoDeCalculoDeCusto tipoDeCalculoDeCusto) throws EDadoInvalidoException {
        this.item = item;
        this.tipoDeCalculoDeCusto = tipoDeCalculoDeCusto;
        calculaMarkup();
        calculaMargemContribuicao();
    }

    private void calculaMarkup() throws EDadoInvalidoException {
        BigDecimal cem = new BigDecimal(100);
        BigDecimal soma = item.getMargem().getCustoFixo().add(item.getMargem().getEmbalagem()).
                add(item.getMargem().getFrete()).add(item.getMargem().getOutrosCustos()).
                add(item.getMargem().getMargem()).add(item.getGrupoFiscal() != null ? item.getGrupoFiscal().getIva().getIva() : BigDecimal.ZERO).
                add(item.getComissao() != null ? item.getComissao().getComissaoRepresentante() : BigDecimal.ZERO).
                add(item.getComissao() != null ? item.getComissao().getComissaoVendedor() : BigDecimal.ZERO);
        if (soma.compareTo(cem) >= 0) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Markup_Max"));
        }
        soma = cem.subtract(soma);
        this.markup = cem.divide(soma, 2, BigDecimal.ROUND_UP);
        this.precoMarkup = tipoDeCalculoDeCusto == TipoDeCalculoDeCusto.CUSTO_MEDIO ? markup.multiply(new ItemService().custoMedio(item))
                : markup.multiply(new ItemService().ultimoCusto(item));

    }

    private void calculaMargemContribuicao() {
        BigDecimal custo = tipoDeCalculoDeCusto == TipoDeCalculoDeCusto.CUSTO_MEDIO ? new ItemService().custoMedio(item)
                : new ItemService().ultimoCusto(item);
        this.margemContribuicao = item.getMargem().getMargem();
        BigDecimal mc = margemContribuicao.divide(new BigDecimal(100), BigDecimal.ROUND_UP);
        BigDecimal mcValor = mc.multiply(custo);
        this.precoMargemContribuicao = mcValor.add(custo);
    }

    public BigDecimal getMarkup() {
        return markup;
    }

    public BigDecimal getPrecoMarkup() {
        return precoMarkup;
    }

    public BigDecimal getMargemContribuicao() {
        return margemContribuicao;
    }

    public BigDecimal getPrecoMargemContribuicao() {
        return precoMargemContribuicao;
    }

}
