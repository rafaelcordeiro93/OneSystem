/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoCobrancaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cobrancaConverter", forClass = CobrancaVariavel.class)
public class CobrancaConverter extends BasicConverter<Cobranca, SelecaoCobrancaView> implements Converter, Serializable {

    public CobrancaConverter() {
        super(Cobranca.class, SelecaoCobrancaView.class);
    }
}
