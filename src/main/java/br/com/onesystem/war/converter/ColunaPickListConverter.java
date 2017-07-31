/*
 * To change this license header, choose License Headers in Coluna Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Coluna;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Rafael Cordeiro
 */
@FacesConverter(value = "colunaPickListConverter", forClass = Coluna.class)
public class ColunaPickListConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Object ret = null;
        if (arg1 instanceof PickList) {
            Object dualList = ((PickList) arg1).getValue();
            DualListModel<Coluna> dl = (DualListModel) dualList;
            for (Object o : dl.getSource()) {
                String key = "" + ((Coluna) o).getNome();
                if (arg2.equals(key)) {
                    ret = o;
                    break;
                }
            }
            if (ret == null) {
                for (Object o : dl.getTarget()) {
                    String key = "" + ((Coluna) o).getNome();
                    if (arg2.equals(key)) {
                        ret = o;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String str = "";
        if (arg2 instanceof Coluna) {
            str = "" + ((Coluna) arg2).getNome();
        }
        return str;
    }
}
