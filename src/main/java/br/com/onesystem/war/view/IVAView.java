package br.com.onesystem.war.view;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.war.builder.IVABV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class IVAView extends BasicMBImpl<IVA, IVABV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new IVABV((IVA) event.getObject());
    }

    public void limparJanela() {
        e = new IVABV();
    }

}
