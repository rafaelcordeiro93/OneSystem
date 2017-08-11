/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.TabelaDeTributacaoBV;
import br.com.onesystem.war.service.TabelaDeTributacaoService;
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
@FacesConverter(value = "tabelaDeTributacaoBVConverter", forClass = TabelaDeTributacaoBV.class)
public class TabelaDeTributacaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<TabelaDeTributacao> lista = new TabelaDeTributacaoService().buscarTabelasDeTributacao();
                if (StringUtils.containsLetter(value)) {
                    for (TabelaDeTributacao tabelaDeTributacao : lista) {
                        if (tabelaDeTributacao.getNome().equals(value)) {
                            return new TabelaDeTributacaoBV(tabelaDeTributacao);
                        }
                    }
                } else {
                    for (TabelaDeTributacao tabelaDeTributacao : lista) {
                        if (tabelaDeTributacao.getId().equals(new Long(value))) {
                            return new TabelaDeTributacaoBV(tabelaDeTributacao);
                        }
                    }
                }
                return new TabelaDeTributacaoBV();
            } catch (Exception e) {
                return new TabelaDeTributacaoBV();
            }
        } else {
            return new TabelaDeTributacaoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((TabelaDeTributacaoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
