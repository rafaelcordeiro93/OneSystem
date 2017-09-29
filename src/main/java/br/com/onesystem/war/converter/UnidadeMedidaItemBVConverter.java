package br.com.onesystem.war.converter;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoUnidadeMedidaItemView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "unidadeMedidaItemBVConverter", forClass = UnidadeMedidaItemBV.class)
public class UnidadeMedidaItemBVConverter extends BasicBVConverter<UnidadeMedidaItem, UnidadeMedidaItemBV, SelecaoUnidadeMedidaItemView> implements Converter, Serializable {

    public UnidadeMedidaItemBVConverter() {
        super(UnidadeMedidaItem.class, UnidadeMedidaItemBV.class, SelecaoUnidadeMedidaItemView.class);
    }

}