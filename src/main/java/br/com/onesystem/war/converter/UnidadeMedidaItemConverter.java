/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoUnidadeMedidaItemView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "unidadeMedidaItemConverter", forClass = UnidadeMedidaItem.class)
public class UnidadeMedidaItemConverter extends BasicConverter<UnidadeMedidaItem, SelecaoUnidadeMedidaItemView> implements Converter, Serializable {

    public UnidadeMedidaItemConverter() {
        super(UnidadeMedidaItem.class, SelecaoUnidadeMedidaItemView.class);
    }
}