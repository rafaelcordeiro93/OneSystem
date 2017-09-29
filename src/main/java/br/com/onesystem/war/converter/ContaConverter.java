/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoContaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "contaConverter", forClass = Conta.class)
public class ContaConverter extends BasicConverter<Conta, SelecaoContaView> implements Converter, Serializable {

    public ContaConverter() {
        super(Conta.class, SelecaoContaView.class);
    }
}
