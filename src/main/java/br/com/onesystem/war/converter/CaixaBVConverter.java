package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.war.builder.CaixaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "caixaBVConverter", forClass = CaixaBV.class)
public class CaixaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Caixa) {
                return new CaixaBV((Caixa) object);
            } else if (object instanceof CaixaBV) {
                return (CaixaBV) object;
            }
        }
        return new CaixaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof CaixaBV) {
                String id = String.valueOf(((CaixaBV) object).getId());
                uic.getAttributes().put(id, (CaixaBV) object);
                return id;
            } else if (object instanceof Caixa) {
                String id = String.valueOf(((Caixa) object).getId());
                uic.getAttributes().put(id, (Caixa) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
