package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SelecaoAjusteDeEstoqueView extends BasicCrudMBImpl<AjusteDeEstoque> implements Serializable {

    @Inject
    private AjusteDeEstoqueService service;

    @PostConstruct
    public void init() {
        beans = service.buscarAjusteDeEstoques();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoAjusteDeEstoque");
    }

    @Override
    public String abrirEdicao() {
        return "ajusteDeEstoque";
    }

    @Override
    public List<AjusteDeEstoque> complete(String query) {
        List<AjusteDeEstoque> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (AjusteDeEstoque a : beans) {
                if (StringUtils.startsWithIgnoreCase(a.getId().toString(), query)) {
                    listaFIltrada.add(a);
                }
            }
        }
        return listaFIltrada;
    }

    public AjusteDeEstoqueService getService() {
        return service;
    }

    public void setService(AjusteDeEstoqueService service) {
        this.service = service;
    }
}
