/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.war.service.impl.BasicConverter;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cotacaoConverter", forClass = Cotacao.class)
public class CotacaoConverter extends BasicConverter<Cotacao> implements Converter, Serializable {

    public CotacaoConverter() {
        super(Cotacao.class);
    }
}
