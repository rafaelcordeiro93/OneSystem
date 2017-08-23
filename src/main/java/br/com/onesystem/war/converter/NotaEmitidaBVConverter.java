package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "notaEmitidaBVConverter", forClass = NotaEmitidaBV.class)
public class NotaEmitidaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof NotaEmitida) {
                return new NotaEmitidaBV((NotaEmitida) object);
            } else if (object instanceof NotaEmitidaBV) {
                return (NotaEmitidaBV) object;
            }
        }
        return new NotaEmitidaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof NotaEmitidaBV) {
                String id = String.valueOf(((NotaEmitidaBV) object).getId());
                uic.getAttributes().put(id, (NotaEmitidaBV) object);
                return id;
            } else if (object instanceof NotaEmitida) {
                String id = String.valueOf(((NotaEmitida) object).getId());
                uic.getAttributes().put(id, (NotaEmitida) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
