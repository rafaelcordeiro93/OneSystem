package br.com.onesystem.war.converter;

import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.PedidoAFornecedoresBV;
import br.com.onesystem.war.service.PedidoAFornecedoresService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pedidoAFornecedoresBVConverter", forClass = PedidoAFornecedoresBV.class)
public class PedidoAFornecedoresBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            PedidoAFornecedoresService service = new PedidoAFornecedoresService();
            try {
                List<PedidoAFornecedores> lista = service.buscarPedidosAFornecedores();

                for (PedidoAFornecedores pedidoAFornecedores : lista) {
                    if (pedidoAFornecedores.getId().equals(new Long(value))) {
                        return new PedidoAFornecedoresBV(pedidoAFornecedores);
                    }
                }

                return new PedidoAFornecedoresBV();
            } catch (Exception e) {
                return new PedidoAFornecedoresBV();
            }
        } else {
            return new PedidoAFornecedoresBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((PedidoAFornecedoresBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
