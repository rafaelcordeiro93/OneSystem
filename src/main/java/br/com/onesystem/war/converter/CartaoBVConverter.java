/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.war.builder.CartaoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoCartaoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cartaoBVConverter", forClass = CartaoBV.class)
public class CartaoBVConverter extends BasicBVConverter<Cartao, CartaoBV, SelecaoCartaoView> implements Converter, Serializable {

    public CartaoBVConverter() {
        super(Cartao.class, CartaoBV.class, SelecaoCartaoView.class);
    }

}
