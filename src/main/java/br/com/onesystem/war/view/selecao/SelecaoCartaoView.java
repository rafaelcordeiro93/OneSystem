package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CartaoService;
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
public class SelecaoCartaoView extends BasicCrudMBImpl<Cartao> implements Serializable {

    @ManagedProperty("#{cartaoService}")
    private CartaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCartaos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCartao");
    }

    @Override
    public String abrirEdicao() {
        return "cartao";
    }
    
     @Override
    public List<Cartao> complete(String query) {
        List<Cartao> listaFIltrada = new ArrayList<>();
        for (Cartao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cartao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }
    
    public CartaoService getService() {
        return service;
    }

    public void setService(CartaoService service) {
        this.service = service;
    }
}
