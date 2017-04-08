package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.OperacaoService;
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
public class SelecaoOperacaoView extends BasicCrudMBImpl<Operacao> implements Serializable {

    @ManagedProperty("#{operacaoService}")
    private OperacaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscar();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoOperacao");
    }
    
    @Override
    public String abrirEdicao(){
        return "operacoes";
    }
    
    @Override
    public List<Operacao> complete(String query) {
        List<Operacao> listaFIltrada = new ArrayList<>();
        for (Operacao b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Operacao m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public OperacaoService getService() {
        return service;
    }

    public void setService(OperacaoService service) {
        this.service = service;
    }
}
