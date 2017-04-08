/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoPrivilegioService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoDePrivilegioConverter", forClass = GrupoDePrivilegio.class)
public class GrupoDePrivilegioConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                GrupoPrivilegioService service = (GrupoPrivilegioService) fc.getExternalContext().getApplicationMap().get("grupoPrivilegioService");
                List<GrupoDePrivilegio> lista = service.buscarGrupoDePrivilegio();
                if (StringUtils.containsLetter(value)) {
                    for (GrupoDePrivilegio grupoDePrivilegio : lista) {
                        if (grupoDePrivilegio.getNome().equals(value)) {
                            return grupoDePrivilegio;
                        }
                    }
                } else {
                    for (GrupoDePrivilegio grupoDePrivilegio : lista) {
                        if (grupoDePrivilegio.getId().equals(new Long(value))) {
                            return grupoDePrivilegio;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um grupoDePrivilegio válido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoDePrivilegio) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
