/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.builder.LayoutDeImpressaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import java.io.Serializable;

/**
 *
 * @author Rafael
 */
public class LayoutDeImpressaoBV implements BuilderView<LayoutDeImpressao>, Serializable {

    private Long id;
    private TipoLayout tipoLayout;
    private String layoutGrafico;
    private String layoutTexto;
    private boolean layoutGraficoEhPadrao = true;
    private TipoImpressao tipoImpressao;

    public LayoutDeImpressaoBV() {
    }

    public LayoutDeImpressaoBV(LayoutDeImpressao l) {
        this.id = l.getId();
        this.tipoLayout = l.getTipoLayout();
        this.layoutGrafico = l.getLayoutGrafico();
        this.layoutTexto = l.getLayoutTexto();
        this.layoutGraficoEhPadrao = l.isLayoutGraficoEhPadrao();
        this.tipoImpressao = l.getTipoImpressao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoLayout getTipoLayout() {
        return tipoLayout;
    }

    public void setTipoLayout(TipoLayout tipoLayout) {
        this.tipoLayout = tipoLayout;
    }

    public String getLayoutGrafico() {
        return layoutGrafico;
    }

    public void setLayoutGrafico(String layoutGrafico) {
        this.layoutGrafico = layoutGrafico;
    }

    public String getLayoutTexto() {
        return layoutTexto;
    }

    public void setLayoutTexto(String layoutTexto) {
        this.layoutTexto = layoutTexto;
    }

    public boolean isLayoutGraficoEhPadrao() {
        return layoutGraficoEhPadrao;
    }

    public void setLayoutGraficoEhPadrao(boolean layoutGraficoEhPadrao) {
        this.layoutGraficoEhPadrao = layoutGraficoEhPadrao;
    }

    public TipoImpressao getTipoImpressao() {
        return tipoImpressao;
    }

    public void setTipoImpressao(TipoImpressao tipoImpressao) {
        this.tipoImpressao = tipoImpressao;
    }

    @Override
    public LayoutDeImpressao construir() throws DadoInvalidoException {
        return new LayoutDeImpressaoBuilder().comLayoutGrafico(layoutGrafico).comLayoutGraficoEhPadrao(layoutGraficoEhPadrao)
                .comLayoutTexto(layoutTexto).comTipoLayout(tipoLayout).comTipoImpressao(tipoImpressao).construir();
    }

    @Override
    public LayoutDeImpressao construirComID() throws DadoInvalidoException {
        return new LayoutDeImpressaoBuilder().comId(id).comLayoutGrafico(layoutGrafico).comLayoutGraficoEhPadrao(layoutGraficoEhPadrao)
                .comLayoutTexto(layoutTexto).comTipoLayout(tipoLayout).comTipoImpressao(tipoImpressao).construir();
    }

}
