package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.TipoDespesaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoTipoDespesaView extends BasicCrudMBImpl<TipoDespesa> implements Serializable {

    @Inject
    private TipoDespesaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarTiposDeDespesa();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoTipoDespesa");
    }
    
    @Override
    public String abrirEdicao() {
        return "tipoDespesa";
    }
    
    @Override
    public List<TipoDespesa> complete(String query) {
        List<TipoDespesa> listaFIltrada = new ArrayList<>();
        for (TipoDespesa b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (TipoDespesa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public TipoDespesaService getService() {
        return service;
    }

    public void setService(TipoDespesaService service) {
        this.service = service;
    }
}
