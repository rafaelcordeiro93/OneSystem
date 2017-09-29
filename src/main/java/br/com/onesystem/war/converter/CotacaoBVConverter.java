/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.war.builder.CotacaoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoCotacaoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cotacaoBVConverter", forClass = CotacaoBV.class)
public class CotacaoBVConverter extends BasicBVConverter<Cotacao, CotacaoBV, SelecaoCotacaoView> implements Converter, Serializable {

    public CotacaoBVConverter() {
        super(Cotacao.class, CotacaoBV.class, SelecaoCotacaoView.class);
    }

}