/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.UsuarioService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "usuarioConverter", forClass = Usuario.class)
public class UsuarioConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                UsuarioService service = (UsuarioService) fc.getExternalContext().getApplicationMap().get("usuarioService");
                List<Usuario> lista = service.buscarUsuarios();
                if (StringUtils.containsLetter(value)) {
                    for (Usuario usuario : lista) {
                        if (usuario.getPessoa().getEmail().equals(value)) {
                            return usuario;
                        }
                    }
                } else {
                    for (Usuario usuario : lista) {
                        if (usuario.getId().equals(new Long(value))) {
                            return usuario;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma usuario válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((Usuario) object).getPessoa().getEmail());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
