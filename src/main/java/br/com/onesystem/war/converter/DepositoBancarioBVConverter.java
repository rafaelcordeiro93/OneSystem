package br.com.onesystem.war.converter;

import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.war.builder.DepositoBancarioBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoDepositoBancarioView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "depositoBancarioBVConverter", forClass = DepositoBancarioBV.class)
public class DepositoBancarioBVConverter extends BasicBVConverter<DepositoBancario, DepositoBancarioBV, SelecaoDepositoBancarioView> implements Converter, Serializable {

    public DepositoBancarioBVConverter() {
        super(DepositoBancario.class, DepositoBancarioBV.class, SelecaoDepositoBancarioView.class);
    }

}