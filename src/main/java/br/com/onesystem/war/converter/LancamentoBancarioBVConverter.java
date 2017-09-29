package br.com.onesystem.war.converter;

import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.war.builder.LancamentoBancarioBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoLancamentoBancarioView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "lancamentoBancarioBVConverter", forClass = LancamentoBancarioBV.class)
public class LancamentoBancarioBVConverter extends BasicBVConverter<LancamentoBancario, LancamentoBancarioBV, SelecaoLancamentoBancarioView> implements Converter, Serializable {

    public LancamentoBancarioBVConverter() {
        super(LancamentoBancario.class, LancamentoBancarioBV.class, SelecaoLancamentoBancarioView.class);
    }

}