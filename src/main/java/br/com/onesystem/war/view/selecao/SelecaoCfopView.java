package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CfopService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoCfopView extends BasicCrudMBImpl<Cfop> implements Serializable {

    @Inject
    private CfopService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCfops();
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoCfop");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/cfop";
    }

    @Override
    public List<Cfop> complete(String query) {
        List<Cfop> listaFIltrada = new ArrayList<>();
        for (Cfop b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cfop m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getCfop().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public CfopService getService() {
        return service;
    }

    public void setService(CfopService service) {
        this.service = service;
    }
}
