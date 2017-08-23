package br.com.onesystem.war.converter;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "unidadeMedidaItemBVConverter", forClass = UnidadeMedidaItemBV.class)
public class UnidadeMedidaItemBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof UnidadeMedidaItem) {
                return new UnidadeMedidaItemBV((UnidadeMedidaItem) object);
            } else if (object instanceof UnidadeMedidaItemBV) {
                return (UnidadeMedidaItemBV) object;
            }
        }
        return new UnidadeMedidaItemBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof UnidadeMedidaItemBV) {
                String id = String.valueOf(((UnidadeMedidaItemBV) object).getId());
                uic.getAttributes().put(id, (UnidadeMedidaItemBV) object);
                return id;
            } else if (object instanceof UnidadeMedidaItem) {
                String id = String.valueOf(((UnidadeMedidaItem) object).getId());
                uic.getAttributes().put(id, (UnidadeMedidaItem) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
