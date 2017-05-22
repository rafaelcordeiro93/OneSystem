package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.BaixaService;
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
@FacesConverter(value = "baixaBVConverter", forClass = BaixaBV.class)
public class BaixaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            BaixaService service = new BaixaService();
            try {
                List<Baixa> lista = service.buscarBaixas();
               
                    for (Baixa baixa : lista) {
                        if (baixa.getId().equals(new Long(value))) {
                            return new BaixaBV(baixa);
                        }
                    }
                
                return new BaixaBV();
            } catch (Exception e) {
                return new BaixaBV();
            }
        } else {
            return new BaixaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((BaixaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
