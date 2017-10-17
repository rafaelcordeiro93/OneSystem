/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoNumeracaoDeNotaFiscalView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael Cordeiro
 */
@FacesConverter(value = "numeracaoDeNotaFiscalConverter", forClass = NumeracaoDeNotaFiscal.class)
public class NumeracaoDeNotaFiscalConverter extends BasicConverter<NumeracaoDeNotaFiscal> implements Converter, Serializable {

    public NumeracaoDeNotaFiscalConverter() {
        super(NumeracaoDeNotaFiscal.class);
    }

}
