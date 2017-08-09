package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoDespesaProvisionadaCambioView extends BasicCrudMBImpl<DespesaProvisionada> implements Serializable {

    @Inject
    private DespesaProvisionadaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDespesaProvisionadasAPagarDivisaoLucro();
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoDespesaProvisionadaCambio");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/despesaProvisionada";
    }
    
    @Override
    public List<DespesaProvisionada> complete(String query) {
        List<DespesaProvisionada> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (DespesaProvisionada m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public DespesaProvisionadaService getService() {
        return service;
    }

    public void setService(DespesaProvisionadaService service) {
        this.service = service;
    }
}
