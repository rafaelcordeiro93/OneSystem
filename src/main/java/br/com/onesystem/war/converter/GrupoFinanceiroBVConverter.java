/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.GrupoFinanceiroBV;
import br.com.onesystem.war.service.GrupoFinanceiroService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFinanceiroBVConverter", forClass = GrupoFinanceiroBV.class)
public class GrupoFinanceiroBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<GrupoFinanceiro> lista = new GrupoFinanceiroService().buscarGruposFinanceiros();
                if (StringUtils.containsLetter(value)) {
                    for (GrupoFinanceiro grupoFinanceiro : lista) {
                        if (grupoFinanceiro.getNome().equals(value)) {
                            return new GrupoFinanceiroBV(grupoFinanceiro);
                        }
                    }
                } else {
                    for (GrupoFinanceiro grupoFinanceiro : lista) {
                        if (grupoFinanceiro.getId().equals(new Long(value))) {
                            return new GrupoFinanceiroBV(grupoFinanceiro);
                        }
                    }
                }
                return new GrupoFinanceiroBV();
            } catch (Exception e) {
                return new GrupoFinanceiroBV();
            }
        } else {
            return new GrupoFinanceiroBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoFinanceiroBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
