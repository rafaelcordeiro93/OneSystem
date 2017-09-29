/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.war.builder.ComissaoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoComissaoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "comissaoBVConverter", forClass = ComissaoBV.class)
public class ComissaoBVConverter extends BasicBVConverter<Comissao, ComissaoBV, SelecaoComissaoView> implements Converter, Serializable {

    public ComissaoBVConverter() {
        super(Comissao.class, ComissaoBV.class, SelecaoComissaoView.class);
    }

}