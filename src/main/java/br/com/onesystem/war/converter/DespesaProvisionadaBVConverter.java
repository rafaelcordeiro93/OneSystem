/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.DespesaProvisionadaBV;
import br.com.onesystem.war.service.DespesaProvisionadaService;
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
@FacesConverter(value = "despesaProvisionadaBVConverter", forClass = DespesaProvisionada.class)
public class DespesaProvisionadaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<DespesaProvisionada> lista = new DespesaProvisionadaService().buscarDespesaProvisionadas();
                if (StringUtils.containsLetter(value)) {
                    for (DespesaProvisionada despesaProvisionada : lista) {
                        if (despesaProvisionada.getHistorico().equals(value)) {
                            return new DespesaProvisionadaBV(despesaProvisionada);
                        }
                    }
                } else {
                    for (DespesaProvisionada despesaProvisionada : lista) {
                        if (despesaProvisionada.getId().equals(new Long(value))) {
                            return new DespesaProvisionadaBV(despesaProvisionada);
                        }
                    }
                }
                return new DespesaProvisionadaBV();
            } catch (Exception e) {
                return new DespesaProvisionadaBV();
            }
        } else {
            return new DespesaProvisionadaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((DespesaProvisionadaBV) object).getHistorico());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
