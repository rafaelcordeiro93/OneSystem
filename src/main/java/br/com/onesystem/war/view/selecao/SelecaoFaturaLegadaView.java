package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.FaturaLegadaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoFaturaLegadaView extends BasicCrudMBImpl<FaturaLegada> implements Serializable {

    @Inject
    private FaturaLegadaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFaturasLegadas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoFaturaLegada");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/faturaLegada";
    }

    @Override
    public List<FaturaLegada> complete(String query) {
        List<FaturaLegada> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (FaturaLegada c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public FaturaLegadaService getService() {
        return service;
    }

    public void setService(FaturaLegadaService service) {
        this.service = service;
    }
}
