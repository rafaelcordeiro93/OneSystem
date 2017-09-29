/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoTipoDespesaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoDespesaConverter", forClass = TipoDespesa.class)
public class TipoDespesaConverter extends BasicConverter<TipoDespesa, SelecaoTipoDespesaView> implements Converter, Serializable {

    public TipoDespesaConverter() {
        super(TipoDespesa.class, SelecaoTipoDespesaView.class);
    }
}