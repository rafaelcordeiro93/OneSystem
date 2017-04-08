package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
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
public class SelecaoAjusteDeEstoqueView extends BasicCrudMBImpl<AjusteDeEstoque> implements Serializable {

    @ManagedProperty("#{ajusteDeEstoqueService}")
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
        List<AjusteDeEstoque> bancosFIltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (AjusteDeEstoque a : beans) {
                if (StringUtils.startsWithIgnoreCase(a.getId().toString(), query)) {
                    bancosFIltrados.add(a);
                }
            }
        }
        return bancosFIltrados;
    }

    public AjusteDeEstoqueService getService() {
        return service;
    }

    public void setService(AjusteDeEstoqueService service) {
        this.service = service;
    }
}
