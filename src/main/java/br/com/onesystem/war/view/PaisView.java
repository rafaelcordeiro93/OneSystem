package br.com.onesystem.war.view;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.war.builder.PaisBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PaisView extends BasicMBImpl<Pais, PaisBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void selecionar(SelectEvent event) {
        e = new PaisBV((Pais) event.getObject());
    }

    public void limparJanela() {
        e = new PaisBV();
    }

}
