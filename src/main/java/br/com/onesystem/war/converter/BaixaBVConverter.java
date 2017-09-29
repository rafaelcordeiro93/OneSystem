package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoBaixaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "baixaBVConverter", forClass = BaixaBV.class)
public class BaixaBVConverter extends BasicBVConverter<Baixa, BaixaBV, SelecaoBaixaView> implements Converter, Serializable {

    public BaixaBVConverter() {
        super(Baixa.class, BaixaBV.class, SelecaoBaixaView.class);
    }

}