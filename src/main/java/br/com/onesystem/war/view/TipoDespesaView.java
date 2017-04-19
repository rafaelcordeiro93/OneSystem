package br.com.onesystem.war.view;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.builder.TipoDespesaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class TipoDespesaView extends BasicMBImpl<TipoDespesa, TipoDespesaBV> implements Serializable {

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof TipoDespesa) {
            e = new TipoDespesaBV((TipoDespesa) event.getObject());
        } else if (obj instanceof GrupoFinanceiro) {
            e.setGrupoFinanceiro((GrupoFinanceiro) event.getObject());
        }
    }

    public void limparJanela() {
        e = new TipoDespesaBV();
    }

}
