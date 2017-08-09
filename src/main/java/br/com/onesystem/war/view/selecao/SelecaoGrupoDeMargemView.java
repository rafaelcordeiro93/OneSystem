package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoDeMargemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoGrupoDeMargemView extends BasicCrudMBImpl<Margem> implements Serializable {

    @Inject
    private GrupoDeMargemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoDeMargens();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoGrupoDeMargem");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/margem";
    }
    
    @Override
    public List<Margem> complete(String query) {
        List<Margem> listaFIltrada = new ArrayList<>();
        for (Margem b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Margem m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public GrupoDeMargemService getService() {
        return service;
    }

    public void setService(GrupoDeMargemService service) {
        this.service = service;
    }
}
