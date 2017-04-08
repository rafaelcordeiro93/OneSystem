package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.BancoService;
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
public class SelecaoBancoView extends BasicCrudMBImpl<Banco> implements Serializable {

    @ManagedProperty("#{bancoService}")
    private BancoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarBancos();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoBanco");
    }
    
    @Override
    public String abrirEdicao() {
        return "banco";
    }
    
    @Override
    public List<Banco> complete(String query) {
        List<Banco> bancosFIltrados = new ArrayList<>();
        for (Banco b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                bancosFIltrados.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Banco m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    bancosFIltrados.add(m);
                }
            }
        }
        return bancosFIltrados;
    }

    public BancoService getService() {
        return service;
    }

    public void setService(BancoService service) {
        this.service = service;
    }
}
