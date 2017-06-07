package br.com.onesystem.war.view;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.war.builder.BancoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class BancoView extends BasicMBImpl<Banco, BancoBV> implements Serializable {

    public void limparJanela() {
        e = new BancoBV();
    }

    public void selecionar(SelectEvent obj) {
        Banco b = (Banco) obj.getObject();
        e = new BancoBV(b);
    }
}
