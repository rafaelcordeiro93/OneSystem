package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoCotacaoView extends BasicCrudMBImpl<Cotacao> implements Serializable {

    @Inject
    private CotacaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCotacoes();
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCotacao");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cotacao";
    }
    
    @Override
    public List<Cotacao> complete(String query) {
        List<Cotacao> listaFIltrada = new ArrayList<>();
        for (Cotacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getConta().getMoeda().getNome(), query) || StringUtils.startsWithIgnoreCase(b.getConta().getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cotacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }
}
