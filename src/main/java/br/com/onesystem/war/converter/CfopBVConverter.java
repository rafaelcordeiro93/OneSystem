/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.war.builder.CfopBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoCfopView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cfopBVConverter", forClass = CfopBV.class)
public class CfopBVConverter extends BasicBVConverter<Cfop, CfopBV, SelecaoCfopView> implements Converter, Serializable {

    public CfopBVConverter() {
        super(Cfop.class, CfopBV.class, SelecaoCfopView.class);
    }

}