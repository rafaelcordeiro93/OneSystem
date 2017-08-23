package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.war.builder.OperacaoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "operacaoBVConverter", forClass = OperacaoBV.class)
public class OperacaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Operacao) {
                return new OperacaoBV((Operacao) object);
            } else if (object instanceof OperacaoBV) {
                return (OperacaoBV) object;
            }
        }
        return new OperacaoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof OperacaoBV) {
                String id = String.valueOf(((OperacaoBV) object).getId());
                uic.getAttributes().put(id, (OperacaoBV) object);
                return id;
            } else if (object instanceof Operacao) {
                String id = String.valueOf(((Operacao) object).getId());
                uic.getAttributes().put(id, (Operacao) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
