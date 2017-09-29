/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoModeloDeRelatorioView;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "modeloDeRelatorioConverter", forClass = ModeloDeRelatorio.class)
public class ModeloDeRelatorioConverter extends BasicConverter<ModeloDeRelatorio, SelecaoModeloDeRelatorioView> implements Converter {

    public ModeloDeRelatorioConverter() {
        super(ModeloDeRelatorio.class, SelecaoModeloDeRelatorioView.class);
    }
}
