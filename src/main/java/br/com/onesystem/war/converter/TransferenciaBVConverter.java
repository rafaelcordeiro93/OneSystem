package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.war.builder.TransferenciaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoTransferenciaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "transferenciaBVConverter", forClass = TransferenciaBV.class)
public class TransferenciaBVConverter extends BasicBVConverter<Transferencia, TransferenciaBV, SelecaoTransferenciaView> implements Converter, Serializable {

    public TransferenciaBVConverter() {
        super(Transferencia.class, TransferenciaBV.class, SelecaoTransferenciaView.class);
    }

}