package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoFinanceiroService;
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
public class SelecaoGrupoFinanceiroReceitaView extends BasicCrudMBImpl<GrupoFinanceiro> implements Serializable {

    @ManagedProperty("#{grupoFinanceiroService}")
    private GrupoFinanceiroService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGruposDeReceitas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupoFinanceiroReceita");
    }
    
    @Override
    public String abrirEdicao() {
        return "grupoFinanceiro";
    }
    
       @Override
    public List<GrupoFinanceiro> complete(String query) {
        List<GrupoFinanceiro> listaFIltrada = new ArrayList<>();
        for (GrupoFinanceiro b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (GrupoFinanceiro m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public GrupoFinanceiroService getService() {
        return service;
    }

    public void setService(GrupoFinanceiroService service) {
        this.service = service;
    }
}
