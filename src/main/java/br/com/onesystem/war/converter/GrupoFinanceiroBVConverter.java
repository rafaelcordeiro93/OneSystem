/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.builder.GrupoFinanceiroBV;
import java.io.Serializable;
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
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof GrupoFinanceiro) {
                return new GrupoFinanceiroBV((GrupoFinanceiro) object);
            } else if (object instanceof GrupoFinanceiroBV) {
                return (GrupoFinanceiroBV) object;
            }
        }
        return new GrupoFinanceiroBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof GrupoFinanceiroBV) {
                String id = String.valueOf(((GrupoFinanceiroBV) object).getId());
                uic.getAttributes().put(id, (GrupoFinanceiroBV) object);
                return id;
            } else if (object instanceof GrupoFinanceiro) {
                String id = String.valueOf(((GrupoFinanceiro) object).getId());
                uic.getAttributes().put(id, (GrupoFinanceiro) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
