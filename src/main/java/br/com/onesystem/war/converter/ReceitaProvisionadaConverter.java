/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.service.impl.BasicConverter;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "receitaProvisionadaConverter", forClass = ReceitaProvisionada.class)
public class ReceitaProvisionadaConverter extends BasicConverter<ReceitaProvisionada> implements Converter, Serializable {

    public ReceitaProvisionadaConverter() {
        super(ReceitaProvisionada.class);
    }

}
