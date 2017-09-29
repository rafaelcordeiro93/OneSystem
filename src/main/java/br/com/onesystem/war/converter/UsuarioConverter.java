/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Usuario;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoUsuarioView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "usuarioConverter", forClass = Usuario.class)
public class UsuarioConverter extends BasicConverter<Usuario, SelecaoUsuarioView> implements Converter, Serializable {

    public UsuarioConverter() {
        super(Usuario.class, SelecaoUsuarioView.class);
    }
}
