/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoOrcamentoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "orcamentoConverter", forClass = Orcamento.class)
public class OrcamentoConverter extends BasicConverter<Orcamento, SelecaoOrcamentoView> implements Converter, Serializable {

    public OrcamentoConverter() {
        super(Orcamento.class, SelecaoOrcamentoView.class);
    }

}
