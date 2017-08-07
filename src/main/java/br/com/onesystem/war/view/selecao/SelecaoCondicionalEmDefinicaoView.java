package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import br.com.onesystem.war.service.CondicionalService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoCondicionalEmDefinicaoView extends BasicCrudMBImpl<Condicional> implements Serializable {

    @Inject
    private CondicionalService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCondicionaisNo(EstadoDeCondicional.EM_DEFINICAO);
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoCondicionalEmDefinicao");
    }

    @Override
    public String abrirEdicao() {
        return "condicional";
    }

    @Override
    public List<Condicional> complete(String query) {
        List<Condicional> comandasFiltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Condicional c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    comandasFiltradas.add(c);
                }
            }
        }
        return comandasFiltradas;
    }

    public CondicionalService getService() {
        return service;
    }

    public void setService(CondicionalService service) {
        this.service = service;
    }
}
