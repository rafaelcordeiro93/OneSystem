package br.com.onesystem.war.view;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.builder.TipoReceitaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ReceitaView extends BasicMBImpl<TipoReceita, TipoReceitaBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();

    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = (Object) event.getObject();
        if (obj instanceof TipoReceita) {
            e = new TipoReceitaBV((TipoReceita) obj);
        } else if (obj instanceof GrupoFinanceiro) {
            e.setGrupoFinanceiro((GrupoFinanceiro) obj);
        }

    }

    @Override
    public void limparJanela() {
        e = new TipoReceitaBV();

    }
}
