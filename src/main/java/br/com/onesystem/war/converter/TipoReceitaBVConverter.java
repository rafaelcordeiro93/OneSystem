/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.war.builder.TipoReceitaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoTipoReceitaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoReceitaBVConverter", forClass = TipoReceitaBV.class)
public class TipoReceitaBVConverter extends BasicBVConverter<TipoReceita, TipoReceitaBV, SelecaoTipoReceitaView> implements Converter, Serializable {

    public TipoReceitaBVConverter() {
        super(TipoReceita.class, TipoReceitaBV.class, SelecaoTipoReceitaView.class);
    }

}