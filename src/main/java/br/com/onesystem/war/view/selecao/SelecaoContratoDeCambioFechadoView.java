package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ContratoDeCambioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoContratoDeCambioFechadoView extends BasicCrudMBImpl<ContratoDeCambio> implements Serializable {

    @Inject
    private ContratoDeCambioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContratosFechadosParaCambio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoContratoDeCambioFechado");
    }

    @Override
    public String abrirEdicao() {
        return "contratoDeCambio";
    }

    @Override
    public List<ContratoDeCambio> complete(String query) {
        List<ContratoDeCambio> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (ContratoDeCambio m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public ContratoDeCambioService getService() {
        return service;
    }

    public void setService(ContratoDeCambioService service) {
        this.service = service;
    }
}
