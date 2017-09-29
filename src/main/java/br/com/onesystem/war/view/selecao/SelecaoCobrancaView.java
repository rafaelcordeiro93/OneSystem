package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CobrancaService;
import br.com.onesystem.war.service.CobrancaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SelecaoCobrancaView extends BasicCrudMBImpl<CobrancaVariavel> implements Serializable {

    @Inject
    private CobrancaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCobrancas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCobranca");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaCobranca";
    }

    @Override
    public List<CobrancaVariavel> complete(String query) {
        List<CobrancaVariavel> cobrancasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (CobrancaVariavel c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    cobrancasFIltradas.add(c);
                }
            }
        }
        return cobrancasFIltradas;
    }

    public CobrancaService getService() {
        return service;
    }

    public void setService(CobrancaService service) {
        this.service = service;
    }
}
