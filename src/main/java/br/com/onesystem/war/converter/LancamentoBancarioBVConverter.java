package br.com.onesystem.war.converter;

import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.war.builder.LancamentoBancarioBV;
import br.com.onesystem.war.service.LancamentoBancarioService;
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
@FacesConverter(value = "lancamentoBancarioBVConverter", forClass = LancamentoBancarioBV.class)
public class LancamentoBancarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            LancamentoBancarioService service = new LancamentoBancarioService();
            try {
                List<LancamentoBancario> lista = service.buscarLancamentoBancarios();

                for (LancamentoBancario lancamentoBancario : lista) {
                    if (lancamentoBancario.getId().equals(new Long(value))) {
                        return new LancamentoBancarioBV(lancamentoBancario);
                    }
                }

                return new LancamentoBancarioBV();
            } catch (Exception e) {
                return new LancamentoBancarioBV();
            }
        } else {
            return new LancamentoBancarioBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((LancamentoBancarioBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
