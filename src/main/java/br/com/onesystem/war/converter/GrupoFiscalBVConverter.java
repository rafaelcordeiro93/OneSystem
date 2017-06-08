/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import br.com.onesystem.war.service.GrupoFiscalService;
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
@FacesConverter(value = "grupoFiscalBVConverter", forClass = GrupoFiscalBV.class)
public class GrupoFiscalBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<GrupoFiscal> lista = new GrupoFiscalService().buscarGrupoFiscais();
                if (StringUtils.containsLetter(value)) {
                    for (GrupoFiscal grupoFiscal : lista) {
                        if (grupoFiscal.getNome().equals(value)) {
                            return new GrupoFiscalBV(grupoFiscal);
                        }
                    }
                } else {
                    for (GrupoFiscal grupoFiscal : lista) {
                        if (grupoFiscal.getId().equals(new Long(value))) {
                            return new GrupoFiscalBV(grupoFiscal);
                        }
                    }
                }
                return new GrupoFiscalBV();
            } catch (Exception e) {
                return new GrupoFiscalBV();
            }
        } else {
            return new GrupoFiscalBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoFiscalBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
