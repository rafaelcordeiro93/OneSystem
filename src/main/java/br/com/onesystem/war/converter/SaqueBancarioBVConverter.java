package br.com.onesystem.war.converter;

import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.war.builder.SaqueBancarioBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoSaqueBancarioView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "saqueBancarioBVConverter", forClass = SaqueBancarioBV.class)
public class SaqueBancarioBVConverter extends BasicBVConverter<SaqueBancario, SaqueBancarioBV, SelecaoSaqueBancarioView> implements Converter, Serializable {

    public SaqueBancarioBVConverter() {
        super(SaqueBancario.class, SaqueBancarioBV.class, SelecaoSaqueBancarioView.class);
    }
}
