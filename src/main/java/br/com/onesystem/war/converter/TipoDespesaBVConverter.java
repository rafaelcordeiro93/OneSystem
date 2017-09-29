/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.war.builder.TipoDespesaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoTipoDespesaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoDespesaBVConverter", forClass = TipoDespesaBV.class)
public class TipoDespesaBVConverter extends BasicBVConverter<TipoDespesa, TipoDespesaBV, SelecaoTipoDespesaView> implements Converter, Serializable {

    public TipoDespesaBVConverter() {
        super(TipoDespesa.class, TipoDespesaBV.class, SelecaoTipoDespesaView.class);
    }

}