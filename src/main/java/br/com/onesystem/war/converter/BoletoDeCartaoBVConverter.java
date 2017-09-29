/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoBoletoDeCartaoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "boletoDeCartaoBVConverter", forClass = BoletoDeCartao.class)
public class BoletoDeCartaoBVConverter extends BasicBVConverter<BoletoDeCartao, BoletoDeCartaoBV, SelecaoBoletoDeCartaoView> implements Converter, Serializable {

    public BoletoDeCartaoBVConverter() {
        super(BoletoDeCartao.class, BoletoDeCartaoBV.class, SelecaoBoletoDeCartaoView.class);
    }

}