package br.com.onesystem.war.view;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.builder.CfopBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class CfopView extends BasicMBImpl<Cfop, CfopBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj != null && obj instanceof Cfop) {
            e = new CfopBV((Cfop) obj);
        }
    }

    @Override
    public void limparJanela() {
        t = null;
        e = new CfopBV();
    }

    public List<OperacaoFisica> getOperacoesFisicas() {
        return Arrays.asList(OperacaoFisica.values());
    }

}
