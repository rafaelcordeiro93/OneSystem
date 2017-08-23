package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.war.builder.FaturaRecebidaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "faturaRecebidaBVConverter", forClass = FaturaRecebidaBV.class)
public class FaturaRecebidaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof FaturaRecebida) {
                return new FaturaRecebidaBV((FaturaRecebida) object);
            } else if (object instanceof FaturaRecebidaBV) {
                return (FaturaRecebidaBV) object;
            }
        }
        return new FaturaRecebidaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof FaturaRecebidaBV) {
                String id = String.valueOf(((FaturaRecebidaBV) object).getId());
                uic.getAttributes().put(id, (FaturaRecebidaBV) object);
                return id;
            } else if (object instanceof FaturaRecebida) {
                String id = String.valueOf(((FaturaRecebida) object).getId());
                uic.getAttributes().put(id, (FaturaRecebida) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
