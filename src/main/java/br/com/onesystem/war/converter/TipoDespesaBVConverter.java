/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.TipoDespesaBV;
import br.com.onesystem.war.service.TipoDespesaService;
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
@FacesConverter(value = "tipoDespesaBVConverter", forClass = TipoDespesaBV.class)
public class TipoDespesaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<TipoDespesa> lista = new TipoDespesaService().buscarTiposDeDespesa();
                if (StringUtils.containsLetter(value)) {
                    for (TipoDespesa tipoDespesa : lista) {
                        if (tipoDespesa.getNome().equals(value)) {
                            return new TipoDespesaBV(tipoDespesa);
                        }
                    }
                } else {
                    for (TipoDespesa tipoDespesa : lista) {
                        if (tipoDespesa.getId().equals(new Long(value))) {
                            return new TipoDespesaBV(tipoDespesa);
                        }
                    }
                }
                return new TipoDespesaBV();
            } catch (Exception e) {
                return new TipoDespesaBV();
            }
        } else {
            return new TipoDespesaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((TipoDespesaBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
