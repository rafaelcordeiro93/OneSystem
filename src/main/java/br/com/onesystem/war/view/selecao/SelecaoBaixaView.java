package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.BaixaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoBaixaView extends BasicCrudMBImpl<Baixa> implements Serializable {

    @Inject
    private BaixaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarBaixas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoBaixa");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/baixa";
    }

    @Override
    public List<Baixa> complete(String query) {
        List<Baixa> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Baixa c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public BaixaService getService() {
        return service;
    }

    public void setService(BaixaService service) {
        this.service = service;
    }
}
