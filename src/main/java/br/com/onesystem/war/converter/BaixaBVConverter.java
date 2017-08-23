package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.war.builder.BaixaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "baixaBVConverter", forClass = BaixaBV.class)
public class BaixaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Baixa) {
                return new BaixaBV((Baixa) object);
            } else if (object instanceof BaixaBV) {
                return (BaixaBV) object;
            }
        }
        return new BaixaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof BaixaBV) {
                String id = String.valueOf(((BaixaBV) object).getId());
                uic.getAttributes().put(id, (BaixaBV) object);
                return id;
            } else if (object instanceof Baixa) {
                String id = String.valueOf(((Baixa) object).getId());
                uic.getAttributes().put(id, (Baixa) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
