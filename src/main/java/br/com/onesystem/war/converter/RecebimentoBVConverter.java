package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.war.builder.RecebimentoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoRecebimentoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "recebimentoBVConverter", forClass = RecebimentoBV.class)
public class RecebimentoBVConverter extends BasicBVConverter<Recebimento, RecebimentoBV, SelecaoRecebimentoView> implements Converter, Serializable {

    public RecebimentoBVConverter() {
        super(Recebimento.class, RecebimentoBV.class, SelecaoRecebimentoView.class);
    }
}