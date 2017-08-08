package br.com.onesystem.war.view;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.war.builder.EstadoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class EstadoView extends BasicMBImpl<Estado, EstadoBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void selecionar(SelectEvent event) {
        e = new EstadoBV((Estado) event.getObject());
    }

    public void limparJanela() {
        e = new EstadoBV();
    }

}
