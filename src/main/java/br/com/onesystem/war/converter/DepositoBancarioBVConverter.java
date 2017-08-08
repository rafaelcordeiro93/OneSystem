package br.com.onesystem.war.converter;

import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.war.builder.DepositoBancarioBV;
import br.com.onesystem.war.service.DepositoBancarioService;
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
@FacesConverter(value = "depositoBancarioBVConverter", forClass = DepositoBancarioBV.class)
public class DepositoBancarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            DepositoBancarioService service = new DepositoBancarioService();
            try {
                List<DepositoBancario> lista = service.buscarDepositoBancarios();

                for (DepositoBancario depositoBancario : lista) {
                    if (depositoBancario.getId().equals(new Long(value))) {
                        return new DepositoBancarioBV(depositoBancario);
                    }
                }

                return new DepositoBancarioBV();
            } catch (Exception e) {
                return new DepositoBancarioBV();
            }
        } else {
            return new DepositoBancarioBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((DepositoBancarioBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
