package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.RecebimentoBV;
import br.com.onesystem.war.service.RecebimentoService;
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
@FacesConverter(value = "recebimentoBVConverter", forClass = RecebimentoBV.class)
public class RecebimentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            RecebimentoService service = new RecebimentoService();
            try {
                List<Recebimento> lista = service.buscarRecebimentos();
               
                    for (Recebimento recebimento : lista) {
                        if (recebimento.getId().equals(new Long(value))) {
                            return new RecebimentoBV(recebimento);
                        }
                    }
                
                return new RecebimentoBV();
            } catch (Exception e) {
                return new RecebimentoBV();
            }
        } else {
            return new RecebimentoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((RecebimentoBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
