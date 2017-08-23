package br.com.onesystem.war.converter;

import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.war.builder.SaqueBancarioBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "saqueBancarioBVConverter", forClass = SaqueBancarioBV.class)
public class SaqueBancarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof SaqueBancario) {
                return new SaqueBancarioBV((SaqueBancario) object);
            } else if (object instanceof SaqueBancarioBV) {
                return (SaqueBancarioBV) object;
            }
        }
        return new SaqueBancarioBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof SaqueBancarioBV) {
                String id = String.valueOf(((SaqueBancarioBV) object).getId());
                uic.getAttributes().put(id, (SaqueBancarioBV) object);
                return id;
            } else if (object instanceof SaqueBancario) {
                String id = String.valueOf(((SaqueBancario) object).getId());
                uic.getAttributes().put(id, (SaqueBancario) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
