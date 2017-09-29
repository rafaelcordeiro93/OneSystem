/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoContaDeEstoqueView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "contaDeEstoqueBVConverter", forClass = ContaDeEstoqueBV.class)
public class ContaDeEstoqueBVConverter extends BasicBVConverter<ContaDeEstoque, ContaDeEstoqueBV, SelecaoContaDeEstoqueView> implements Converter, Serializable {

    public ContaDeEstoqueBVConverter() {
        super(ContaDeEstoque.class, ContaDeEstoqueBV.class, SelecaoContaDeEstoqueView.class);
    }

}