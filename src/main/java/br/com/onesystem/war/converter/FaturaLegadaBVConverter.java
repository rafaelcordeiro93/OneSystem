package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.war.builder.FaturaLegadaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "faturaLegadaBVConverter", forClass = FaturaLegadaBV.class)
public class FaturaLegadaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof FaturaLegada) {
                return new FaturaLegadaBV((FaturaLegada) object);
            } else if (object instanceof FaturaLegadaBV) {
                return (FaturaLegadaBV) object;
            }
        }
        return new FaturaLegadaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof FaturaLegadaBV) {
                String id = String.valueOf(((FaturaLegadaBV) object).getId());
                uic.getAttributes().put(id, (FaturaLegadaBV) object);
                return id;
            } else if (object instanceof FaturaLegada) {
                String id = String.valueOf(((FaturaLegada) object).getId());
                uic.getAttributes().put(id, (FaturaLegada) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
