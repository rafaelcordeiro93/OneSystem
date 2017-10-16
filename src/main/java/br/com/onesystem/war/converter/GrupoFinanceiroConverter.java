/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.service.impl.BasicConverter;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFinanceiroConverter", forClass = GrupoFinanceiro.class)
public class GrupoFinanceiroConverter extends BasicConverter<GrupoFinanceiro> implements Converter, Serializable {

    public GrupoFinanceiroConverter() {
        super(GrupoFinanceiro.class);
    }
}
