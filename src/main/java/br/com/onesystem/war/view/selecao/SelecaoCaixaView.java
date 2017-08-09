package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CaixaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoCaixaView extends BasicCrudMBImpl<Caixa> implements Serializable {

    @Inject
    private CaixaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCaixas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCaixa");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/caixa";
    }

    @Override
    public List<Caixa> complete(String query) {
        List<Caixa> caixasFIltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Caixa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    caixasFIltrados.add(m);
                }
            }
        }
        return caixasFIltrados;
    }

    public CaixaService getService() {
        return service;
    }

    public void setService(CaixaService service) {
        this.service = service;
    }
}
