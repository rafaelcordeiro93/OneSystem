/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoCartaoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cartaoConverter", forClass = Cartao.class)
public class CartaoConverter extends BasicConverter<Cartao, SelecaoCartaoView> implements Converter, Serializable {

    public CartaoConverter() {
        super(Cartao.class, SelecaoCartaoView.class);
    }
}
