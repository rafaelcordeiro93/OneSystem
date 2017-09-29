/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.war.builder.CepBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoCepView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cepBVConverter", forClass = CepBV.class)
public class CepBVConverter extends BasicBVConverter<Cep, CepBV, SelecaoCepView> implements Converter, Serializable {

    public CepBVConverter() {
        super(Cep.class, CepBV.class, SelecaoCepView.class);
    }

}