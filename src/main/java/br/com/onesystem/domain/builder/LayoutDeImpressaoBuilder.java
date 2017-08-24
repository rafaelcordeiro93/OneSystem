/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLayout;

/**
 *
 * @author Rafael
 */
public class LayoutDeImpressaoBuilder {

    private Long id;
    private TipoLayout tipoLayout;
    private String layoutGrafico;
    private String layoutTexto;
    private boolean layoutGraficoEhPadrao;

    public LayoutDeImpressaoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public LayoutDeImpressaoBuilder comTipoLayout(TipoLayout tipoLayout) {
        this.tipoLayout = tipoLayout;
        return this;
    }

    public LayoutDeImpressaoBuilder comLayoutGrafico(String layoutGrafico) {
        this.layoutGrafico = layoutGrafico;
        return this;
    }

    public LayoutDeImpressaoBuilder comLayoutTexto(String layoutTexto) {
        this.layoutTexto = layoutTexto;
        return this;
    }

    public LayoutDeImpressaoBuilder comLayoutGraficoEhPadrao(boolean layoutGraficoEhPadrao) {
        this.layoutGraficoEhPadrao = layoutGraficoEhPadrao;
        return this;
    }

    public LayoutDeImpressao construir() throws DadoInvalidoException {
        return new LayoutDeImpressao(id, tipoLayout, layoutGrafico, layoutTexto, layoutGraficoEhPadrao);
    }

}
