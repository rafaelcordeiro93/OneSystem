package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.OperacaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class SelecaoOperacaoView extends BasicCrudMBImpl<Operacao> implements Serializable {

    @Inject
    private OperacaoService service;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = service.buscar();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoOperacao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/operacoes";
    }

    @Override
    public List<Operacao> complete(String query) {
        buscarDados();
        List<Operacao> listaFiltrada = new ArrayList<>();
        for (Operacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFiltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Operacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFiltrada.add(m);
                }
            }
        }
        return listaFiltrada;
    }

    public OperacaoService getService() {
        return service;
    }

    public void setService(OperacaoService service) {
        this.service = service;
    }
}
