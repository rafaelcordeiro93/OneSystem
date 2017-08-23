package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import java.io.Serializable;
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
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof GrupoDePrivilegio) {
                return new GrupoDePrivilegioBV((GrupoDePrivilegio) object);
            } else if (object instanceof GrupoDePrivilegioBV) {
                return (GrupoDePrivilegioBV) object;
            }
        }
        return new GrupoDePrivilegioBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof GrupoDePrivilegioBV) {
                String id = String.valueOf(((GrupoDePrivilegioBV) object).getId());
                uic.getAttributes().put(id, (GrupoDePrivilegioBV) object);
                return id;
            } else if (object instanceof GrupoDePrivilegio) {
                String id = String.valueOf(((GrupoDePrivilegio) object).getId());
                uic.getAttributes().put(id, (GrupoDePrivilegio) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
