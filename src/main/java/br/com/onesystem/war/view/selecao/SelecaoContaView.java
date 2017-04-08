package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ContaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SelecaoContaView extends BasicCrudMBImpl<Conta> implements Serializable {

    @ManagedProperty("#{contaService}")
    private ContaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoConta");
    }

    @Override
    public String abrirEdicao() {
        return "conta";
    }

    @Override
    public List<Conta> complete(String query) {
        List<Conta> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Conta c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        } else {
            for (Conta c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getNome(), query) || StringUtils.startsWithIgnoreCase(c.getMoeda().getNome(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public ContaService getService() {
        return service;
    }

    public void setService(ContaService service) {
        this.service = service;
    }
}
