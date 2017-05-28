package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.CaixaBV;
import br.com.onesystem.war.service.CaixaService;
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
@FacesConverter(value = "caixaBVConverter", forClass = CaixaBV.class)
public class CaixaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            CaixaService service = new CaixaService();
            try {
                List<Caixa> lista = service.buscarCaixas();
               
                    for (Caixa caixa : lista) {
                        if (caixa.getId().equals(new Long(value))) {
                            return new CaixaBV(caixa);
                        }
                    }
                
                return new CaixaBV();
            } catch (Exception e) {
                return new CaixaBV();
            }
        } else {
            return new CaixaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((CaixaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
