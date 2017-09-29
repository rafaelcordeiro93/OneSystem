package br.com.onesystem.war.converter;

import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.war.builder.PedidoAFornecedoresBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoPedidoAFornecedoresView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "pedidoAFornecedoresBVConverter", forClass = PedidoAFornecedoresBV.class)
public class PedidoAFornecedoresBVConverter extends BasicBVConverter<PedidoAFornecedores, PedidoAFornecedoresBV, SelecaoPedidoAFornecedoresView> implements Converter, Serializable {

    public PedidoAFornecedoresBVConverter() {
        super(PedidoAFornecedores.class, PedidoAFornecedoresBV.class, SelecaoPedidoAFornecedoresView.class);
    }

}
