package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.builder.FilialBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoFilialView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "filialBVConverter", forClass = FilialBV.class)
public class FilialBVConverter extends BasicBVConverter<Filial, FilialBV, SelecaoFilialView> implements Converter, Serializable {

    public FilialBVConverter() {
        super(Filial.class, FilialBV.class, SelecaoFilialView.class);
    }

}