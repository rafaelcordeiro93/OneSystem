package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoDespesaProvisionadaCambioView extends BasicCrudMBImpl<DespesaProvisionada> implements Serializable {

    @ManagedProperty("#{despesaProvisionadaService}")
    private DespesaProvisionadaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDespesaProvisionadasAPagarDivisaoLucro();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoDespesaProvisionadaCambio");
    }
    
    @Override
    public String abrirEdicao() {
        return "despesaProvisionada";
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
