package br.com.onesystem.war.view;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.valueobjects.TipoBandeira;
import br.com.onesystem.war.builder.MoedaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class MoedaView extends BasicMBImpl<Moeda, MoedaBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void selecionar(SelectEvent event) {
        e = new MoedaBV((Moeda) event.getObject());
    }

    public List<TipoBandeira> getBandeiras() {
        return Arrays.asList(TipoBandeira.values());
    }

    public void limparJanela() {
        e = new MoedaBV();
    }

}
