/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.GrupoDeMargemBV;
import br.com.onesystem.war.service.GrupoDeMargemService;
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
@FacesConverter(value = "margemBVConverter", forClass = GrupoDeMargemBV.class)
public class MargemBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Margem> lista = new GrupoDeMargemService().buscarGrupoDeMargens();
                if (StringUtils.containsLetter(value)) {
                    for (Margem margem : lista) {
                        if (margem.getNome().equals(value)) {
                            return new GrupoDeMargemBV(margem);
                        }
                    }
                } else {
                    for (Margem margem : lista) {
                        if (margem.getId().equals(new Long(value))) {
                            return new GrupoDeMargemBV(margem);
                        }
                    }
                }
                return new GrupoDeMargemBV();
            } catch (Exception e) {
                return new GrupoDeMargemBV();
            }
        } else {
            return new GrupoDeMargemBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoDeMargemBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
