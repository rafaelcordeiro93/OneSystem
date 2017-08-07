package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.war.builder.FaturaEmitidaBV;
import br.com.onesystem.war.service.FaturaEmitidaService;
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
@FacesConverter(value = "faturaEmitidaBVConverter", forClass = FaturaEmitidaBV.class)
public class FaturaEmitidaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            FaturaEmitidaService service = new FaturaEmitidaService();
            try {
                List<FaturaEmitida> lista = service.buscarFaturasEmitidas();
               
                    for (FaturaEmitida faturaEmitida : lista) {
                        if (faturaEmitida.getId().equals(new Long(value))) {
                            return new FaturaEmitidaBV(faturaEmitida);
                        }
                    }
                
                return new FaturaEmitidaBV();
            } catch (Exception e) {
                return new FaturaEmitidaBV();
            }
        } else {
            return new FaturaEmitidaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((FaturaEmitidaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
