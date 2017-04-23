package br.com.onesystem.war.view;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.war.builder.ContaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ContaView extends BasicMBImpl<Conta, ContaBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        if (event == null) {
            limparJanela();
        }
        Object obj = event.getObject();
        if (obj instanceof Conta) {
            e = new ContaBV((Conta) event.getObject());
        } else if (obj instanceof Banco) {
            e.setBanco((Banco) obj);
        } else if (obj instanceof Moeda) {
            e.setMoeda((Moeda) obj);
        }
    }

    public void limparJanela() {
        e = new ContaBV();
    }

}
