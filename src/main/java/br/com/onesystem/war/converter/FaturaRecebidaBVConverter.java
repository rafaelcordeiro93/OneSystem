package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.war.builder.FaturaRecebidaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoFaturaRecebidaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "faturaRecebidaBVConverter", forClass = FaturaRecebidaBV.class)
public class FaturaRecebidaBVConverter extends BasicBVConverter<FaturaRecebida, FaturaRecebidaBV, SelecaoFaturaRecebidaView> implements Converter, Serializable {

    public FaturaRecebidaBVConverter() {
        super(FaturaRecebida.class, FaturaRecebidaBV.class, SelecaoFaturaRecebidaView.class);
    }

}