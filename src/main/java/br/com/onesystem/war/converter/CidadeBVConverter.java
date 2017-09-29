/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.war.builder.CidadeBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoCidadeView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cidadeBVConverter", forClass = CidadeBV.class)
public class CidadeBVConverter extends BasicBVConverter<Cidade, CidadeBV, SelecaoCidadeView> implements Converter, Serializable {

    public CidadeBVConverter() {
        super(Cidade.class, CidadeBV.class, SelecaoCidadeView.class);
    }

}
