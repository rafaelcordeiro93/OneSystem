package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.war.builder.FaturaEmitidaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoFaturaEmitidaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "faturaEmitidaBVConverter", forClass = FaturaEmitidaBV.class)
public class FaturaEmitidaBVConverter extends BasicBVConverter<FaturaEmitida, FaturaEmitidaBV, SelecaoFaturaEmitidaView> implements Converter, Serializable {

    public FaturaEmitidaBVConverter() {
        super(FaturaEmitida.class, FaturaEmitidaBV.class, SelecaoFaturaEmitidaView.class);
    }

}