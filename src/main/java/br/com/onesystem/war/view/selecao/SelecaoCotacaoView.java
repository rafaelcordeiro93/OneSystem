package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoCotacaoView extends BasicCrudMBImpl<Cotacao> implements Serializable {

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCotacoes();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCotacao");
    }

    @Override
    public String abrirEdicao() {
        return "cotacao";
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
