/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.war.builder.TabelaDeTributacaoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoTabelaDeTributacaoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tabelaDeTributacaoBVConverter", forClass = TabelaDeTributacaoBV.class)
public class TabelaDeTributacaoBVConverter extends BasicBVConverter<TabelaDeTributacao, TabelaDeTributacaoBV, SelecaoTabelaDeTributacaoView> implements Converter, Serializable {

    public TabelaDeTributacaoBVConverter() {
        super(TabelaDeTributacao.class, TabelaDeTributacaoBV.class, SelecaoTabelaDeTributacaoView.class);
    }

}