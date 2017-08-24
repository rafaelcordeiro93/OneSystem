package br.com.onesystem.war.view;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.builder.ListaDePrecoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ListaDePrecoView extends BasicMBImpl<ListaDePreco, ListaDePrecoBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ListaDePreco) {
            e = new ListaDePrecoBV((ListaDePreco) event.getObject());
        }
    }

    public void limparJanela() {
        e = new ListaDePrecoBV();
    }

}
