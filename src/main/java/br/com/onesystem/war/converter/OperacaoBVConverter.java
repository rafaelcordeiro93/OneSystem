package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoOperacaoView;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "operacaoBVConverter", forClass = OperacaoBV.class)
public class OperacaoBVConverter extends BasicBVConverter<Operacao, OperacaoBV, SelecaoOperacaoView> implements Converter, Serializable {

    public OperacaoBVConverter() {
        super(Operacao.class, OperacaoBV.class, SelecaoOperacaoView.class);
    }
}