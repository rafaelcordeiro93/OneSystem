package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.builder.FilialBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class FilialView extends BasicMBImpl<Filial, FilialBV> implements Serializable {
    
    @PostConstruct
    public void init() {
        limparJanela();
    }
    
    public void limparJanela() {
        e = new FilialBV();
    }
    
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Filial) {
            e = new FilialBV((Filial) obj);
        } else if (obj instanceof Cep) {
            e.setCep((Cep) obj);
        }        
    }
    
}
