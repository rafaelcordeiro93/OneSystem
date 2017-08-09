package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoFiscalService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoGrupoFiscalView extends BasicCrudMBImpl<GrupoFiscal> implements Serializable {

    @Inject
    private GrupoFiscalService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoFiscais();
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoGrupoFiscal");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/grupoFiscal";
    }

    @Override
    public List<GrupoFiscal> complete(String query) {
        List<GrupoFiscal> listaFIltrada = new ArrayList<>();
        for (GrupoFiscal b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (GrupoFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public GrupoFiscalService getService() {
        return service;
    }

    public void setService(GrupoFiscalService service) {
        this.service = service;
    }
}
