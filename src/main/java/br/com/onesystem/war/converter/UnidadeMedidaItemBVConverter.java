package br.com.onesystem.war.converter;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "unidadeMedidaItemBVConverter", forClass = UnidadeMedidaItemBV.class)
public class UnidadeMedidaItemBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            UnidadeMedidaItemService service = new UnidadeMedidaItemService();
            try {
                List<UnidadeMedidaItem> lista = service.buscarUnidadeMedidaItens();
               
                    for (UnidadeMedidaItem unidadeMedidaItem : lista) {
                        if (unidadeMedidaItem.getId().equals(new Long(value))) {
                            return new UnidadeMedidaItemBV(unidadeMedidaItem);
                        }
                    }
                
                return new UnidadeMedidaItemBV();
            } catch (Exception e) {
                return new UnidadeMedidaItemBV();
            }
        } else {
            return new UnidadeMedidaItemBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((UnidadeMedidaItemBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
