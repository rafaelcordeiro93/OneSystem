package br.com.onesystem.war.converter;

import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.war.builder.SaqueBancarioBV;
import br.com.onesystem.war.service.SaqueBancarioService;
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
@FacesConverter(value = "saqueBancarioBVConverter", forClass = SaqueBancarioBV.class)
public class SaqueBancarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            SaqueBancarioService service = new SaqueBancarioService();
            try {
                List<SaqueBancario> lista = service.buscarSaqueBancarios();

                for (SaqueBancario saqueBancario : lista) {
                    if (saqueBancario.getId().equals(new Long(value))) {
                        return new SaqueBancarioBV(saqueBancario);
                    }
                }

                return new SaqueBancarioBV();
            } catch (Exception e) {
                return new SaqueBancarioBV();
            }
        } else {
            return new SaqueBancarioBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((SaqueBancarioBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
