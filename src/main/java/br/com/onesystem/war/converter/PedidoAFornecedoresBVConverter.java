package br.com.onesystem.war.converter;

import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.war.builder.PedidoAFornecedoresBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pedidoAFornecedoresBVConverter", forClass = PedidoAFornecedoresBV.class)
public class PedidoAFornecedoresBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof PedidoAFornecedores) {
                return new PedidoAFornecedoresBV((PedidoAFornecedores) object);
            } else if (object instanceof PedidoAFornecedoresBV) {
                return (PedidoAFornecedoresBV) object;
            }
        }
        return new PedidoAFornecedoresBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof PedidoAFornecedoresBV) {
                String id = String.valueOf(((PedidoAFornecedoresBV) object).getId());
                uic.getAttributes().put(id, (PedidoAFornecedoresBV) object);
                return id;
            } else if (object instanceof PedidoAFornecedores) {
                String id = String.valueOf(((PedidoAFornecedores) object).getId());
                uic.getAttributes().put(id, (PedidoAFornecedores) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
