package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.NotaEmitidaService;
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
@FacesConverter(value = "notaEmitidaBVConverter", forClass = NotaEmitidaBV.class)
public class NotaEmitidaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            NotaEmitidaService service = new NotaEmitidaService();
            try {
                List<NotaEmitida> lista = service.buscarNotasEmitidas();
               
                    for (NotaEmitida notaEmitida : lista) {
                        if (notaEmitida.getId().equals(new Long(value))) {
                            return new NotaEmitidaBV(notaEmitida);
                        }
                    }
                
                return new NotaEmitidaBV();
            } catch (Exception e) {
                return new NotaEmitidaBV();
            }
        } else {
            return new NotaEmitidaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((NotaEmitidaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
