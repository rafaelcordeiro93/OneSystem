/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoGrupoFiscalView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFiscalConverter", forClass = GrupoFiscal.class)
public class GrupoFiscalConverter extends BasicConverter<GrupoFiscal, SelecaoGrupoFiscalView> implements Converter, Serializable {

    public GrupoFiscalConverter() {
        super(GrupoFiscal.class, SelecaoGrupoFiscalView.class);
    }
}
