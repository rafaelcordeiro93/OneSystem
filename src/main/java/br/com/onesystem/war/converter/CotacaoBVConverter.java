/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.CotacaoBV;
import br.com.onesystem.war.service.CotacaoService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cotacaoBVConverter", forClass = CotacaoBV.class)
public class CotacaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Cotacao> lista = new CotacaoService().buscarCotacoes();
                if (!StringUtils.containsLetter(value)) {
                    for (Cotacao cotacao : lista) {
                        if (cotacao.getId().equals(new Long(value))) {
                            return new CotacaoBV(cotacao);
                        }
                    }
                }
                return new CotacaoBV();
            } catch (Exception e) {
                return new CotacaoBV();
            }
        } else {
            return new CotacaoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((CotacaoBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
