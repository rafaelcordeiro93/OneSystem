package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.war.builder.PagamentoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pagamentoBVConverter", forClass = PagamentoBV.class)
public class PagamentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Pagamento) {
                return new PagamentoBV((Pagamento) object);
            } else if (object instanceof PagamentoBV) {
                return (PagamentoBV) object;
            }
        }
        return new PagamentoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof PagamentoBV) {
                String id = String.valueOf(((PagamentoBV) object).getId());
                uic.getAttributes().put(id, (PagamentoBV) object);
                return id;
            } else if (object instanceof Pagamento) {
                String id = String.valueOf(((Pagamento) object).getId());
                uic.getAttributes().put(id, (Pagamento) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
