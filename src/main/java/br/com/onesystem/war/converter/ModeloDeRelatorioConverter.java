/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.war.service.impl.BasicConverter;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "modeloDeRelatorioConverter", forClass = ModeloDeRelatorio.class)
public class ModeloDeRelatorioConverter extends BasicConverter<ModeloDeRelatorio> implements Converter {

    public ModeloDeRelatorioConverter() {
        super(ModeloDeRelatorio.class);
    }
}
