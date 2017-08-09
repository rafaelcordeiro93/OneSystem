package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.war.builder.TransferenciaBV;
import br.com.onesystem.war.service.TransferenciaService;
import java.io.Serializable;
import java.util.List;
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
        if (value != null && value.trim().length() > 0) {
            TransferenciaService service = new TransferenciaService();
            try {
                List<Transferencia> lista = service.buscarTransferencias();

                for (Transferencia transferencia : lista) {
                    if (transferencia.getId().equals(new Long(value))) {
                        return new TransferenciaBV(transferencia);
                    }
                }

                return new TransferenciaBV();
            } catch (Exception e) {
                return new TransferenciaBV();
            }
        } else {
            return new TransferenciaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((TransferenciaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
