package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.FaturaRecebida;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.FaturaRecebidaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoFaturaRecebidaView extends BasicCrudMBImpl<FaturaRecebida> implements Serializable {

    @Inject
    private FaturaRecebidaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFaturasEmitidas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoFaturaRecebida");
    }

    @Override
    public String abrirEdicao() {
        return "faturaRecebida";
    }

    @Override
    public List<FaturaRecebida> complete(String query) {
        List<FaturaRecebida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (FaturaRecebida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public FaturaRecebidaService getService() {
        return service;
    }

    public void setService(FaturaRecebidaService service) {
        this.service = service;
    }
}
