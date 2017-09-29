/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Comanda;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoComandaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "comandaConverter", forClass = Comanda.class)
public class ComandaConverter extends BasicConverter<Comanda, SelecaoComandaView> implements Converter, Serializable {

    public ComandaConverter() {
        super(Comanda.class, SelecaoComandaView.class);
    }
}
