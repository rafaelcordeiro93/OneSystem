/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.builder.ReceitaProvisionadaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoReceitaProvisionadaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "receitaProvisionadaBVConverter", forClass = ReceitaProvisionada.class)
public class ReceitaProvisionadaBVConverter extends BasicBVConverter<ReceitaProvisionada, ReceitaProvisionadaBV, SelecaoReceitaProvisionadaView> implements Converter, Serializable {

    public ReceitaProvisionadaBVConverter() {
        super(ReceitaProvisionada.class, ReceitaProvisionadaBV.class, SelecaoReceitaProvisionadaView.class);
    }
}
