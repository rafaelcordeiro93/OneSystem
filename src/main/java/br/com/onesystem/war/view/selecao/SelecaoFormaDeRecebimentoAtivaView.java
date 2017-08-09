package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.FormaDeRecebimentoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoFormaDeRecebimentoAtivaView extends BasicCrudMBImpl<FormaDeRecebimento> implements Serializable {

    @Inject
    private FormaDeRecebimentoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFormasDeRecebimentoAtivas();
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoFormaDeRecebimentoAtiva");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/formaDeRecebimento";
    }
    
    @Override
    public List<FormaDeRecebimento> complete(String query) {
        List<FormaDeRecebimento> listaFIltrada = new ArrayList<>();
        for (FormaDeRecebimento b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (FormaDeRecebimento m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public FormaDeRecebimentoService getService() {
        return service;
    }

    public void setService(FormaDeRecebimentoService service) {
        this.service = service;
    }
}
