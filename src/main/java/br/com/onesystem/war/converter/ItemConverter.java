/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Item;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoItemView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "itemConverter", forClass = Item.class)
public class ItemConverter extends BasicConverter<Item, SelecaoItemView> implements Converter, Serializable {

    public ItemConverter() {
        super(Item.class, SelecaoItemView.class);
    }
}
