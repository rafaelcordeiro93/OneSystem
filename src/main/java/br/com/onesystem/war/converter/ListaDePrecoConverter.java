/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoListaDePrecoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "listaDePrecoConverter", forClass = ListaDePreco.class)
public class ListaDePrecoConverter extends BasicConverter<ListaDePreco, SelecaoListaDePrecoView> implements Converter, Serializable {

    public ListaDePrecoConverter() {
        super(ListaDePreco.class, SelecaoListaDePrecoView.class);
    }
}
