/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.war.builder.DespesaProvisionadaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoDespesaProvisionadaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "despesaProvisionadaBVConverter", forClass = DespesaProvisionada.class)
public class DespesaProvisionadaBVConverter extends BasicBVConverter<DespesaProvisionada, DespesaProvisionadaBV, SelecaoDespesaProvisionadaView> implements Converter, Serializable {

    public DespesaProvisionadaBVConverter() {
        super(DespesaProvisionada.class, DespesaProvisionadaBV.class, SelecaoDespesaProvisionadaView.class);
    }

}