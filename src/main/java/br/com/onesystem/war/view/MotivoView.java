package br.com.onesystem.war.view;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.war.builder.MotivoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class MotivoView extends BasicMBImpl<Motivo, MotivoBV> implements Serializable {

    public void limparJanela() {
        e = new MotivoBV();
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Motivo) {
            e = new MotivoBV((Motivo) obj);
        }
    }
}
