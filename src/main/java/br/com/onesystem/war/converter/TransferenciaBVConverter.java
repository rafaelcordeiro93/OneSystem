package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.war.builder.TransferenciaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "transferenciaBVConverter", forClass = TransferenciaBV.class)
public class TransferenciaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Transferencia) {
                return new TransferenciaBV((Transferencia) object);
            } else if (object instanceof TransferenciaBV) {
                return (TransferenciaBV) object;
            }
        }
        return new TransferenciaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof TransferenciaBV) {
                String id = String.valueOf(((TransferenciaBV) object).getId());
                uic.getAttributes().put(id, (TransferenciaBV) object);
                return id;
            } else if (object instanceof Transferencia) {
                String id = String.valueOf(((Transferencia) object).getId());
                uic.getAttributes().put(id, (Transferencia) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
