package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.service.ChequeService;
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
@FacesConverter(value = "chequeBVConverter", forClass = ChequeBV.class)
public class ChequeBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            ChequeService service = new ChequeService();
            try {
                List<Cheque> lista = service.buscarCheques();

                for (Cheque cheque : lista) {
                    if (cheque.getId().equals(new Long(value))) {
                        return new ChequeBV(cheque);
                    }
                }

                return new ChequeBV();
            } catch (Exception e) {
                return new ChequeBV();
            }
        } else {
            return new ChequeBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ChequeBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
