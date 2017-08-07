package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.builder.FilialBV;
import br.com.onesystem.war.service.FilialService;
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
@FacesConverter(value = "filialBVConverter", forClass = FilialBV.class)
public class FilialBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            FilialService service = new FilialService();
            try {
                List<Filial> lista = service.buscarFiliais();
               
                    for (Filial filial : lista) {
                        if (filial.getId().equals(new Long(value))) {
                            return new FilialBV(filial);
                        }
                    }
                
                return new FilialBV();
            } catch (Exception e) {
                return new FilialBV();
            }
        } else {
            return new FilialBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((FilialBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
