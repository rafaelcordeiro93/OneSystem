package br.com.onesystem.war.view;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class UnidadeMedidaItemView extends BasicMBImpl<UnidadeMedidaItem, UnidadeMedidaItemBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof UnidadeMedidaItem) {
            e = new UnidadeMedidaItemBV((UnidadeMedidaItem) event.getObject());
        }
    }

    public void limparJanela() {
        e = new UnidadeMedidaItemBV();
    }

}
