package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.war.builder.FaturaLegadaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoFaturaLegadaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "faturaLegadaBVConverter", forClass = FaturaLegadaBV.class)
public class FaturaLegadaBVConverter extends BasicBVConverter<FaturaLegada, FaturaLegadaBV, SelecaoFaturaLegadaView> implements Converter, Serializable {

    public FaturaLegadaBVConverter() {
        super(FaturaLegada.class, FaturaLegadaBV.class, SelecaoFaturaLegadaView.class);
    }

}