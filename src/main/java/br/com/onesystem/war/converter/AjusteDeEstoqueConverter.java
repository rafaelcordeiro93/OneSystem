/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoAjusteDeEstoqueView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "aJusteDeEstoqueConverter", forClass = AjusteDeEstoque.class)
public class AjusteDeEstoqueConverter extends BasicConverter<AjusteDeEstoque, SelecaoAjusteDeEstoqueView> implements Converter, Serializable {

    public AjusteDeEstoqueConverter() {
        super(AjusteDeEstoque.class, SelecaoAjusteDeEstoqueView.class);
    }
}
