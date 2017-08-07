package br.com.onesystem.war.view;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class GrupoFiscalView extends BasicMBImpl<GrupoFiscal, GrupoFiscalBV> implements Serializable {
    
    @PostConstruct
    public void init() {
        limparJanela();
    }
    
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = (Object) event.getObject();
        if (obj instanceof GrupoFiscal) {
            e = new GrupoFiscalBV((GrupoFiscal) obj);
        } else if (obj instanceof IVA) {
            e.setIva((IVA) obj);
        }        
    }
    
    @Override
    public void limparJanela() {
        e = new GrupoFiscalBV();
    }
    
}
