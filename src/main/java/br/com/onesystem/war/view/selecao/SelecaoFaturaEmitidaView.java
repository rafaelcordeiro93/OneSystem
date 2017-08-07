package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.FaturaEmitidaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoFaturaEmitidaView extends BasicCrudMBImpl<FaturaEmitida> implements Serializable {

    @Inject
    private FaturaEmitidaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFaturasEmitidas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoFaturaEmitida");
    }

    @Override
    public String abrirEdicao() {
        return "faturaEmitida";
    }

    @Override
    public List<FaturaEmitida> complete(String query) {
        List<FaturaEmitida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (FaturaEmitida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public FaturaEmitidaService getService() {
        return service;
    }

    public void setService(FaturaEmitidaService service) {
        this.service = service;
    }
}
