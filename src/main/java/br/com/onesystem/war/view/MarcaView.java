package br.com.onesystem.war.view;

import br.com.onesystem.dao.MarcaDAO;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.MarcaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped //javax.faces.view.ViewScoped;
public class MarcaView extends BasicMBImpl<Marca, MarcaBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Marca) {
            e = new MarcaBV((Marca) event.getObject());
        }
    }

    public void limparJanela() {
        e = new MarcaBV();
    }

}
