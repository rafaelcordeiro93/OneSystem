package br.com.onesystem.war.view;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.war.builder.GrupoDeMargemBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class GrupoDeMargemView extends BasicMBImpl<Margem, GrupoDeMargemBV> implements Serializable {
 
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Margem) {
            e = new GrupoDeMargemBV((Margem) obj);
        }
    }

    public void limparJanela() {
        e = new GrupoDeMargemBV();
    }

}
