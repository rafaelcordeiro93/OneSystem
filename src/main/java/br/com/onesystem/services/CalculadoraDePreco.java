/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.war.service.EstoqueService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
public class CalculadoraDePreco implements Serializable {

    private Item item;
    private BigDecimal markup;
    private BigDecimal margemContribuicao;
    private BigDecimal precoMarkup;
    private BigDecimal precoMargemContribuicao;
    private TipoDeCalculoDeCusto tipoDeCalculoDeCusto;
    private BundleUtil bundle = new BundleUtil();

    @Inject
    private EstoqueService service;

    public CalculadoraDePreco() {
    }

    public void calcula(Item item, TipoDeCalculoDeCusto tipoDeCalculoDeCusto) throws DadoInvalidoException {
        this.item = item;
        this.tipoDeCalculoDeCusto = tipoDeCalculoDeCusto;
        calculaMarkup();
        calculaMargemContribuicao();
    }

    private void calculaMarkup() throws DadoInvalidoException {
        BigDecimal cem = new BigDecimal(100);
        BigDecimal soma = item.getMargem().getCustoFixo().add(item.getMargem().getEmbalagem()).
                add(item.getMargem().getFrete()).add(item.getMargem().getOutrosCustos()).
                add(item.getMargem().getMargem()).add(item.getGrupoFiscal() != null ? item.getGrupoFiscal().getTabelaDeTributacaoPadrao() != null
                ? item.getGrupoFiscal().getTabelaDeTributacaoPadrao().getIva() : BigDecimal.ZERO : BigDecimal.ZERO).
                add(item.getComissao() != null ? item.getComissao().getComissaoRepresentante() : BigDecimal.ZERO).
                add(item.getComissao() != null ? item.getComissao().getComissaoVendedor() : BigDecimal.ZERO);
        if (soma.compareTo(cem) >= 0) {
            throw new EDadoInvalidoException(bundle.getMessage("Markup_Max"));
        }
        soma = cem.subtract(soma);
        this.markup = cem.divide(soma, 2, BigDecimal.ROUND_UP);

        if (TipoDeCalculoDeCusto.CUSTO_MEDIO == tipoDeCalculoDeCusto) {
            BigDecimal custoMedio = service.buscaCustoMedioDeItem(item, new Date());
            if (custoMedio.compareTo(BigDecimal.ZERO) == 0) {
                throw new ADadoInvalidoException(bundle.getMessage("Custo_Medio_Zero"));
            } else {
                this.precoMarkup = custoMedio;
            }

        } else {
            BigDecimal ultimoCusto = service.buscaUltimoCustoItem(item, new Date());
            if (ultimoCusto.compareTo(BigDecimal.ZERO) == 0) {
                throw new ADadoInvalidoException(bundle.getMessage("Ultimo_Custo_Zero"));
            } else {
                this.precoMarkup = ultimoCusto;
            }
        }
    }

    private void calculaMargemContribuicao() throws DadoInvalidoException {
        BigDecimal custo = BigDecimal.ZERO;
        if (TipoDeCalculoDeCusto.CUSTO_MEDIO == tipoDeCalculoDeCusto) {
            BigDecimal custoMedio = service.buscaCustoMedioDeItem(item, new Date());
            if (custoMedio.compareTo(BigDecimal.ZERO) == 0) {
                throw new ADadoInvalidoException(bundle.getMessage("Custo_Medio_Zero"));
            } else {
                custo = custoMedio;
            }

        } else {
            BigDecimal ultimoCusto = service.buscaUltimoCustoItem(item, new Date());
            if (ultimoCusto.compareTo(BigDecimal.ZERO) == 0) {
                throw new ADadoInvalidoException(bundle.getMessage("Ultimo_Custo_Zero"));
            } else {
                custo = ultimoCusto;
            }
        }

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
