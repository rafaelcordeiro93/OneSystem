package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.builder.RecebimentoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "recebimentoBVConverter", forClass = RecebimentoBV.class)
public class RecebimentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Pessoa) {
                return new PessoaBV((Pessoa) object);
            } else if (object instanceof PessoaBV) {
                return (PessoaBV) object;
            }
        }
        return new PessoaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof PessoaBV) {
                String id = String.valueOf(((PessoaBV) object).getId());
                uic.getAttributes().put(id, (PessoaBV) object);
                return id;
            } else if (object instanceof Pessoa) {
                String id = String.valueOf(((Pessoa) object).getId());
                uic.getAttributes().put(id, (Pessoa) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
