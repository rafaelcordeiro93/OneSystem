package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PagamentoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoPagamentoView extends BasicCrudMBImpl<Pagamento> implements Serializable {

    @Inject
    private PagamentoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPagamentos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoPagamento");
    }

    @Override
    public String abrirEdicao() {
        return "/receitaProvisionada";
    }
    
    @Override
    public List<Pagamento> complete(String query) {
        List<Pagamento> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Pagamento m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public PagamentoService getService() {
        return service;
    }

    public void setService(PagamentoService service) {
        this.service = service;
    }
}
