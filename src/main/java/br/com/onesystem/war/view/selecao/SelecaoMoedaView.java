package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.MoedaService;
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
public class SelecaoMoedaView extends BasicCrudMBImpl<Moeda> implements Serializable {

    @ManagedProperty("#{moedaService}")
    private MoedaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarMoedas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoMoeda");
    }
    
    @Override
    public String abrirEdicao() {
        return "moeda";
    }
    
    @Override
    public List<Moeda> complete(String query) {
        List<Moeda> moedasFIltradas = new ArrayList<>();
        for (Moeda m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getNome(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Moeda m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }


    public MoedaService getService() {
        return service;
    }

    public void setService(MoedaService service) {
        this.service = service;
    }
}
