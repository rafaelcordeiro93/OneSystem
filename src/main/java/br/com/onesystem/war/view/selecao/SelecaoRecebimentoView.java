package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.RecebimentoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoRecebimentoView extends BasicCrudMBImpl<Recebimento> implements Serializable {

    @Inject
    private RecebimentoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarRecebimentos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoRecebimento");
    }

    @Override
    public String abrirEdicao() {
        return "/receitaProvisionada";
    }
    
    @Override
    public List<Recebimento> complete(String query) {
        List<Recebimento> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Recebimento m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public RecebimentoService getService() {
        return service;
    }

    public void setService(RecebimentoService service) {
        this.service = service;
    }
}
