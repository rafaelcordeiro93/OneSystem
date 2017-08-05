package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ConhecimentoDeFreteService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoConhecimentoDeFreteView extends BasicCrudMBImpl<ConhecimentoDeFrete> implements Serializable {

    @Inject
    private ConhecimentoDeFreteService service;

    @PostConstruct
    public void init() {
        beans = service.buscarConhecimentoDeFrete();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoConhecimentoDeFrete");
    }

    @Override
    public String abrirEdicao() {
        return "conhecimentoDeFrete";
    }

    @Override
    public List<ConhecimentoDeFrete> complete(String query) {
        List<ConhecimentoDeFrete> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (ConhecimentoDeFrete m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public ConhecimentoDeFreteService getService() {
        return service;
    }

    public void setService(ConhecimentoDeFreteService service) {
        this.service = service;
    }
}
