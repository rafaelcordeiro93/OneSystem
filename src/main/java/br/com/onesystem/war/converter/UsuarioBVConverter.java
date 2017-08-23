package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.builder.UsuarioBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "usuarioBVConverter", forClass = UsuarioBV.class)
public class UsuarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Usuario) {
                return new UsuarioBV((Usuario) object);
            } else if (object instanceof UsuarioBV) {
                return (UsuarioBV) object;
            }
        }
        return new UsuarioBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof UsuarioBV) {
                String id = String.valueOf(((UsuarioBV) object).getId());
                uic.getAttributes().put(id, (UsuarioBV) object);
                return id;
            } else if (object instanceof Usuario) {
                String id = String.valueOf(((Usuario) object).getId());
                uic.getAttributes().put(id, (Usuario) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
