package br.com.onesystem.war.view;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.war.builder.GrupoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class GrupoView extends BasicMBImpl<Grupo, GrupoBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Grupo) {
            e = new GrupoBV((Grupo) event.getObject());
        }
    }

    public void limparJanela() {
        e = new GrupoBV();
    }
}
