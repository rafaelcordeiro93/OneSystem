package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.builder.UsuarioBV;
import br.com.onesystem.war.service.UsuarioService;
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
@FacesConverter(value = "usuarioBVConverter", forClass = UsuarioBV.class)
public class UsuarioBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            UsuarioService service = new UsuarioService();
            try {
                List<Usuario> lista = service.buscarUsuarios();

                for (Usuario usuario : lista) {
                    if (usuario.getId().equals(new Long(value))) {
                        return new UsuarioBV(usuario);
                    }
                }

                return new UsuarioBV();
            } catch (Exception e) {
                return new UsuarioBV();
            }
        } else {
            return new UsuarioBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((UsuarioBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
