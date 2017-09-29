package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoChequeView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "chequeBVConverter", forClass = ChequeBV.class)
public class ChequeBVConverter extends BasicBVConverter<Cheque, ChequeBV, SelecaoChequeView> implements Converter, Serializable {

    public ChequeBVConverter() {
        super(Cheque.class, ChequeBV.class, SelecaoChequeView.class);
    }

}