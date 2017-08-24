package br.com.onesystem.war.view;

import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.LayoutDeImpressaoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class LayoutDeImpressaoView extends BasicMBImpl<LayoutDeImpressao, LayoutDeImpressaoBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof LayoutDeImpressao) {
            e = new LayoutDeImpressaoBV((LayoutDeImpressao) obj);
        }
    }

    @Override
    public void limparJanela() {
        e = new LayoutDeImpressaoBV();
    }
    
    public List<TipoLayout> getTipoLayouts(){
        return Arrays.asList(TipoLayout.values());
    }

}
