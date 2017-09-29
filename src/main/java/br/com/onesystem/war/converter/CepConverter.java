/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoCepView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cepConverter", forClass = Cep.class)
public class CepConverter extends BasicConverter<Cep, SelecaoCepView> implements Converter, Serializable {

    public CepConverter() {
        super(Cep.class, SelecaoCepView.class);
    }
}
