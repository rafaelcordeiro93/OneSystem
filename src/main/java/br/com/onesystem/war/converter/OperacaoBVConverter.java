package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.war.service.OperacaoService;
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
@FacesConverter(value = "operacaoBVConverter", forClass = OperacaoBV.class)
public class OperacaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            OperacaoService service = new OperacaoService();
            try {
                List<Operacao> lista = service.buscar();

                for (Operacao operacao : lista) {
                    if (operacao.getId().equals(new Long(value))) {
                        return new OperacaoBV(operacao);
                    }
                }

                return new OperacaoBV();
            } catch (Exception e) {
                return new OperacaoBV();
            }
        } else {
            return new OperacaoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((OperacaoBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
