/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.builder.ListaDePrecoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoListaDePrecoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "listaDePrecoBVConverter", forClass = ListaDePrecoBV.class)
public class ListaDePrecoBVConverter extends BasicBVConverter<ListaDePreco, ListaDePrecoBV, SelecaoListaDePrecoView> implements Converter, Serializable {

    public ListaDePrecoBVConverter() {
        super(ListaDePreco.class, ListaDePrecoBV.class, SelecaoListaDePrecoView.class);
    }

}
