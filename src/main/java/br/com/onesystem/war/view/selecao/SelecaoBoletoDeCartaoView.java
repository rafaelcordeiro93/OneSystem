package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.BoletoDeCartaoService;
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
public class SelecaoBoletoDeCartaoView extends BasicCrudMBImpl<BoletoDeCartao> implements Serializable {

    @ManagedProperty("#{boletoDeCartaoService}")
    private BoletoDeCartaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarBoletoDeCartaos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoBoletoDeCartao");
    }
    
    @Override
    public String abrirEdicao() {
        return "boletoDeCartao";
    }

    public BoletoDeCartaoService getService() {
        return service;
    }
    
     @Override
    public List<BoletoDeCartao> complete(String query) {
        List<BoletoDeCartao> boletosFIltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (BoletoDeCartao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    boletosFIltrados.add(m);
                }
            }
        }
        return boletosFIltrados;
    }

    public void setService(BoletoDeCartaoService service) {
        this.service = service;
    }
}
