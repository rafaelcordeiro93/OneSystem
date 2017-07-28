package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.war.builder.FaturaRecebidaBV;
import br.com.onesystem.war.service.FaturaRecebidaService;
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
@FacesConverter(value = "faturaRecebidaBVConverter", forClass = FaturaRecebidaBV.class)
public class FaturaRecebidaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            FaturaRecebidaService service = new FaturaRecebidaService();
            try {
                List<FaturaRecebida> lista = service.buscarFaturasEmitidas();
               
                    for (FaturaRecebida faturaRecebida : lista) {
                        if (faturaRecebida.getId().equals(new Long(value))) {
                            return new FaturaRecebidaBV(faturaRecebida);
                        }
                    }
                
                return new FaturaRecebidaBV();
            } catch (Exception e) {
                return new FaturaRecebidaBV();
            }
        } else {
            return new FaturaRecebidaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((FaturaRecebidaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
