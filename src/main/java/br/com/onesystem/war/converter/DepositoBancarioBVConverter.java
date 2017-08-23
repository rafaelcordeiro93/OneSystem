package br.com.onesystem.war.converter;

import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.war.builder.DepositoBancarioBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "depositoBancarioBVConverter", forClass = DepositoBancarioBV.class)
public class DepositoBancarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof DepositoBancario) {
                return new DepositoBancarioBV((DepositoBancario) object);
            } else if (object instanceof DepositoBancarioBV) {
                return (DepositoBancarioBV) object;
            }
        }
        return new DepositoBancarioBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof DepositoBancarioBV) {
                String id = String.valueOf(((DepositoBancarioBV) object).getId());
                uic.getAttributes().put(id, (DepositoBancarioBV) object);
                return id;
            } else if (object instanceof DepositoBancario) {
                String id = String.valueOf(((DepositoBancario) object).getId());
                uic.getAttributes().put(id, (DepositoBancario) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
