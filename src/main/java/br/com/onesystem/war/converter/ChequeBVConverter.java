package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.war.builder.ChequeBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "chequeBVConverter", forClass = ChequeBV.class)
public class ChequeBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Cheque) {
                return new ChequeBV((Cheque) object);
            } else if (object instanceof ChequeBV) {
                return (ChequeBV) object;
            }
        }
        return new ChequeBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ChequeBV) {
                String id = String.valueOf(((ChequeBV) object).getId());
                uic.getAttributes().put(id, (ChequeBV) object);
                return id;
            } else if (object instanceof Cheque) {
                String id = String.valueOf(((Cheque) object).getId());
                uic.getAttributes().put(id, (Cheque) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
