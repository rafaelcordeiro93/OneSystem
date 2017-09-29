package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.war.builder.PagamentoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoPagamentoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pagamentoBVConverter", forClass = PagamentoBV.class)
public class PagamentoBVConverter extends BasicBVConverter<Pagamento, PagamentoBV, SelecaoPagamentoView> implements Converter, Serializable {

    public PagamentoBVConverter() {
        super(Pagamento.class, PagamentoBV.class, SelecaoPagamentoView.class);
    }
}
