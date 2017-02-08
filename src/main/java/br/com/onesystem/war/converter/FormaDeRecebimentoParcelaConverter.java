/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "formaDeRecebimentoParcelaConverter", forClass = TipoFormaDeRecebimentoParcela.class)
public class FormaDeRecebimentoParcelaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<TipoFormaDeRecebimentoParcela> lista = Arrays.asList(TipoFormaDeRecebimentoParcela.values());
                for (TipoFormaDeRecebimentoParcela tipoFormaDeRecebimento : lista) {
                    if (tipoFormaDeRecebimento.getId().equals(new Long(value))) {
                        return tipoFormaDeRecebimento;
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((TipoFormaDeRecebimentoParcela) object).getId());
        } else {
            return null;
        }
    }
}
