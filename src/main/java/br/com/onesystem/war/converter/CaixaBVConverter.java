package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.war.builder.CaixaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoCaixaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "caixaBVConverter", forClass = CaixaBV.class)
public class CaixaBVConverter extends BasicBVConverter<Caixa, CaixaBV, SelecaoCaixaView> implements Converter, Serializable {

    public CaixaBVConverter() {
        super(Caixa.class, CaixaBV.class, SelecaoCaixaView.class);
    }

}