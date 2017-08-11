package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.TabelaDeTributacaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoTabelaDeTributacaoView extends BasicCrudMBImpl<TabelaDeTributacao> implements Serializable {

    @Inject
    private TabelaDeTributacaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarTabelasDeTributacao();
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoTabelaDeTributacao");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/contabil/tabelaDeTributacao";
    }
    
    @Override
    public List<TabelaDeTributacao> complete(String query) {
        List<TabelaDeTributacao> listaFIltrada = new ArrayList<>();
        for (TabelaDeTributacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (TabelaDeTributacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public TabelaDeTributacaoService getService() {
        return service;
    }

    public void setService(TabelaDeTributacaoService service) {
        this.service = service;
    }
}
