/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoTipoReceitaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoReceitaConverter", forClass = TipoReceita.class)
public class TipoReceitaConverter extends BasicConverter<TipoReceita, SelecaoTipoReceitaView> implements Converter, Serializable {

    public TipoReceitaConverter() {
        super(TipoReceita.class, SelecaoTipoReceitaView.class);
    }
}