package br.com.onesystem.war.converter;

import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.war.builder.LancamentoBancarioBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "lancamentoBancarioBVConverter", forClass = LancamentoBancarioBV.class)
public class LancamentoBancarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof LancamentoBancario) {
                return new LancamentoBancarioBV((LancamentoBancario) object);
            } else if (object instanceof LancamentoBancarioBV) {
                return (LancamentoBancarioBV) object;
            }
        }
        return new LancamentoBancarioBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof LancamentoBancarioBV) {
                String id = String.valueOf(((LancamentoBancarioBV) object).getId());
                uic.getAttributes().put(id, (LancamentoBancarioBV) object);
                return id;
            } else if (object instanceof LancamentoBancario) {
                String id = String.valueOf(((LancamentoBancario) object).getId());
                uic.getAttributes().put(id, (LancamentoBancario) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
