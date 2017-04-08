package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.domain.Comissao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ComissaoService;
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
public class SelecaoComissaoView extends BasicCrudMBImpl<Comissao> implements Serializable {

    @ManagedProperty("#{comissaoService}")
    private ComissaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarComissao();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoComissao");
    }
    
    @Override
    public String abrirEdicao() {
        return "comissao";
    }

    @Override
    public List<Comissao> complete(String query) {
        List<Comissao> listaFIltrada = new ArrayList<>();
        for (Comissao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Comissao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public ComissaoService getService() {
        return service;
    }

    public void setService(ComissaoService service) {
        this.service = service;
    }
}
