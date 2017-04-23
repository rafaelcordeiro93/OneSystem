package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.DepositoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoDepositoView extends BasicCrudMBImpl<Deposito> implements Serializable {

    @Inject
    private DepositoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDepositos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoDeposito");
    }
    
    @Override
    public String abrirEdicao() {
        return "deposito";
    }
    
    @Override
    public List<Deposito> complete(String query) {
        List<Deposito> listaFIltrada = new ArrayList<>();
        for (Deposito b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Deposito m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public DepositoService getService() {
        return service;
    }

    public void setService(DepositoService service) {
        this.service = service;
    }
}
