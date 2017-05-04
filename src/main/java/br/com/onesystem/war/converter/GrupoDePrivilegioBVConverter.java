/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import br.com.onesystem.war.service.GrupoPrivilegioService;
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
@FacesConverter(value = "grupoDePrivilegioBVConverter", forClass = GrupoDePrivilegioBV.class)
public class GrupoDePrivilegioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                GrupoPrivilegioService service = (GrupoPrivilegioService) fc.getExternalContext().getApplicationMap().get("grupoPrivilegioService");
                List<GrupoDePrivilegio> lista = service.buscarGrupoDePrivilegio();
                if (StringUtils.containsLetter(value)) {
                    for (GrupoDePrivilegio grupoDePrivilegio : lista) {
                        if (grupoDePrivilegio.getNome().equals(value)) {
                            return new GrupoDePrivilegioBV(grupoDePrivilegio);
                        }
                    }
                } else {
                    for (GrupoDePrivilegio grupoDePrivilegio : lista) {
                        if (grupoDePrivilegio.getId().equals(new Long(value))) {
                            return new GrupoDePrivilegioBV(grupoDePrivilegio);
                        }
                    }
                }
                return new GrupoDePrivilegioBV();
            } catch (Exception e) {
                return new GrupoDePrivilegioBV();
            }
        } else {
            return new GrupoDePrivilegioBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoDePrivilegioBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
