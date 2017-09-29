package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.builder.UsuarioBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoUsuarioView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "usuarioBVConverter", forClass = UsuarioBV.class)
public class UsuarioBVConverter extends BasicBVConverter<Usuario, UsuarioBV, SelecaoUsuarioView> implements Converter, Serializable {

    public UsuarioBVConverter() {
        super(Usuario.class, UsuarioBV.class, SelecaoUsuarioView.class);
    }

}