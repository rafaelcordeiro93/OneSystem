/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.war.builder.BancoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoBancoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "bancoBVConverter", forClass = BancoBV.class)
public class BancoBVConverter extends BasicBVConverter<Banco, BancoBV, SelecaoBancoView> implements Converter, Serializable {

    public BancoBVConverter() {
        super(Banco.class, BancoBV.class, SelecaoBancoView.class);
    }

}