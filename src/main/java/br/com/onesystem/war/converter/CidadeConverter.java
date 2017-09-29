/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoCidadeView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cidadeConverter", forClass = Cidade.class)
public class CidadeConverter extends BasicConverter<Cidade, SelecaoCidadeView> implements Converter, Serializable {

    public CidadeConverter() {
        super(Cidade.class, SelecaoCidadeView.class);
    }
}
