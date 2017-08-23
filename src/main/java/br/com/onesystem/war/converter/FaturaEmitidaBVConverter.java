package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.war.builder.FaturaEmitidaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "faturaEmitidaBVConverter", forClass = FaturaEmitidaBV.class)
public class FaturaEmitidaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof FaturaEmitida) {
                return new FaturaEmitidaBV((FaturaEmitida) object);
            } else if (object instanceof FaturaEmitidaBV) {
                return (FaturaEmitidaBV) object;
            }
        }
        return new FaturaEmitidaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof FaturaEmitidaBV) {
                String id = String.valueOf(((FaturaEmitidaBV) object).getId());
                uic.getAttributes().put(id, (FaturaEmitidaBV) object);
                return id;
            } else if (object instanceof FaturaEmitida) {
                String id = String.valueOf(((FaturaEmitida) object).getId());
                uic.getAttributes().put(id, (FaturaEmitida) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
