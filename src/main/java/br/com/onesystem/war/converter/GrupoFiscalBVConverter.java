/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoGrupoFiscalView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFiscalBVConverter", forClass = GrupoFiscalBV.class)
public class GrupoFiscalBVConverter extends BasicBVConverter<GrupoFiscal, GrupoFiscalBV, SelecaoGrupoFiscalView> implements Converter, Serializable {

    public GrupoFiscalBVConverter() {
        super(GrupoFiscal.class, GrupoFiscalBV.class, SelecaoGrupoFiscalView.class);
    }

}