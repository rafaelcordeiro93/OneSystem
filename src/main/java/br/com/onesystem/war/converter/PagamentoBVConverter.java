package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.war.builder.PagamentoBV;
import br.com.onesystem.war.service.PagamentoService;
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
@FacesConverter(value = "pagamentoBVConverter", forClass = PagamentoBV.class)
public class PagamentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            PagamentoService service = new PagamentoService();
            try {
                List<Pagamento> lista = service.buscarPagamentos();

                for (Pagamento pagamento : lista) {
                    if (pagamento.getId().equals(new Long(value))) {
                        return new PagamentoBV(pagamento);
                    }
                }

                return new PagamentoBV();
            } catch (Exception e) {
                return new PagamentoBV();
            }
        } else {
            return new PagamentoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((PagamentoBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
