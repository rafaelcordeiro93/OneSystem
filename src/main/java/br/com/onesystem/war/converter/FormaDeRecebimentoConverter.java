/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoFormaDeRecebimentoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "formaDeRecebimentoConverter", forClass = FormaDeRecebimento.class)
public class FormaDeRecebimentoConverter extends BasicConverter<FormaDeRecebimento, SelecaoFormaDeRecebimentoView> implements Converter, Serializable {

    public FormaDeRecebimentoConverter() {
        super(FormaDeRecebimento.class, SelecaoFormaDeRecebimentoView.class);
    }
}
