package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoNotaEmitidaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "notaEmitidaBVConverter", forClass = NotaEmitidaBV.class)
public class NotaEmitidaBVConverter extends BasicBVConverter<NotaEmitida, NotaEmitidaBV, SelecaoNotaEmitidaView> implements Converter, Serializable {

    public NotaEmitidaBVConverter() {
        super(NotaEmitida.class, NotaEmitidaBV.class, SelecaoNotaEmitidaView.class);
    }
}
