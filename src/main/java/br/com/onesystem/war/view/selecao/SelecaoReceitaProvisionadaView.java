package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoReceitaProvisionadaView extends BasicCrudMBImpl<ReceitaProvisionada> implements Serializable {

    @Inject
    private ReceitaProvisionadaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarReceitaProvisionadasAReceber();
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoReceitaProvisionada");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/receitaProvisionada";
    }
    
    @Override
    public List<ReceitaProvisionada> complete(String query) {
        List<ReceitaProvisionada> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (ReceitaProvisionada m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

}
