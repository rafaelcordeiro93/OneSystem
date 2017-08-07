/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.CidadeBV;
import br.com.onesystem.war.service.CidadeService;
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
@FacesConverter(value = "cidadeBVConverter", forClass = CidadeBV.class)
public class CidadeBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Cidade> lista = new CidadeService().buscarCidades();
                if (StringUtils.containsLetter(value)) {
                    for (Cidade cidade : lista) {
                        if (cidade.getNome().equals(value)) {
                            return new CidadeBV(cidade);
                        }
                    }
                } else {
                    for (Cidade cidade : lista) {
                        if (cidade.getId().equals(new Long(value))) {
                            return new CidadeBV(cidade);
                        }
                    }
                }
                return new CidadeBV();
            } catch (Exception e) {
                return new CidadeBV();
            }
        } else {
            return new CidadeBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((CidadeBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
