package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.builder.FilialBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "filialBVConverter", forClass = FilialBV.class)
public class FilialBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Filial) {
                return new FilialBV((Filial) object);
            } else if (object instanceof FilialBV) {
                return (FilialBV) object;
            }
        }
        return new FilialBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof FilialBV) {
                String id = String.valueOf(((FilialBV) object).getId());
                uic.getAttributes().put(id, (FilialBV) object);
                return id;
            } else if (object instanceof Filial) {
                String id = String.valueOf(((Filial) object).getId());
                uic.getAttributes().put(id, (Filial) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
