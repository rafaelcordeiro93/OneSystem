/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Item;
import br.com.onesystem.war.builder.ItemBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "itemBVConverter", forClass = ItemBV.class)
public class ItemBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Item) {
                return new ItemBV((Item) object);
            } else if (object instanceof ItemBV) {
                return (ItemBV) object;
            }
        }
        return new ItemBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ItemBV) {
                String id = String.valueOf(((ItemBV) object).getId());
                uic.getAttributes().put(id, (ItemBV) object);
                return id;
            } else if (object instanceof Item) {
                String id = String.valueOf(((Item) object).getId());
                uic.getAttributes().put(id, (Item) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
