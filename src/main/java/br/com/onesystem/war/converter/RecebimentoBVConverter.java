package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.war.builder.RecebimentoBV;
import br.com.onesystem.war.builder.RecebimentoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "recebimentoBVConverter", forClass = RecebimentoBV.class)
public class RecebimentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Recebimento) {
                return new RecebimentoBV((Recebimento) object);
            } else if (object instanceof RecebimentoBV) {
                return (RecebimentoBV) object;
            }
        }
        return new RecebimentoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof RecebimentoBV) {
                String id = String.valueOf(((RecebimentoBV) object).getId());
                uic.getAttributes().put(id, (RecebimentoBV) object);
                return id;
            } else if (object instanceof Recebimento) {
                String id = String.valueOf(((Recebimento) object).getId());
                uic.getAttributes().put(id, (Recebimento) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
