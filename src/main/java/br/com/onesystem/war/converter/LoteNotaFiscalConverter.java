/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoLoteNotaFiscalView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael Cordeiro
 */
@FacesConverter(value = "loteNotaFiscalConverter", forClass = LoteNotaFiscal.class)
public class LoteNotaFiscalConverter extends BasicConverter<LoteNotaFiscal, SelecaoLoteNotaFiscalView> implements Converter, Serializable {

    public LoteNotaFiscalConverter() {
        super(LoteNotaFiscal.class, SelecaoLoteNotaFiscalView.class);
    }

}
