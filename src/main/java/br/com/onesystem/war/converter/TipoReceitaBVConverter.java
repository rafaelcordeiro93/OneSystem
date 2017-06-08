/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.TipoReceitaBV;
import br.com.onesystem.war.service.TipoReceitaService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoReceitaBVConverter", forClass = TipoReceitaBV.class)
public class TipoReceitaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<TipoReceita> lista = new TipoReceitaService().buscarTiposDeReceita();
                if (StringUtils.containsLetter(value)) {
                    for (TipoReceita tipoReceita : lista) {
                        if (tipoReceita.getNome().equals(value)) {
                            return new TipoReceitaBV(tipoReceita);
                        }
                    }
                } else {
                    for (TipoReceita tipoReceita : lista) {
                        if (tipoReceita.getId().equals(new Long(value))) {
                            return new TipoReceitaBV(tipoReceita);
                        }
                    }
                }
                return new TipoReceitaBV();
            } catch (Exception e) {
                return new TipoReceitaBV();
            }
        } else {
            return new TipoReceitaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((TipoReceitaBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
