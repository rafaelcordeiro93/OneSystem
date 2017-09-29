/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.war.builder.FormaDeRecebimentoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoFormaDeRecebimentoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "formaDeRecebimentoBVConverter", forClass = FormaDeRecebimento.class)
public class FormaDeRecebimentoBVConverter extends BasicBVConverter<FormaDeRecebimento, FormaDeRecebimentoBV, SelecaoFormaDeRecebimentoView> implements Converter, Serializable {

    public FormaDeRecebimentoBVConverter() {
        super(FormaDeRecebimento.class, FormaDeRecebimentoBV.class, SelecaoFormaDeRecebimentoView.class);
    }

}