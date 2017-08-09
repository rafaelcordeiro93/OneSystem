package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.BancoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoBancoView extends BasicCrudMBImpl<Banco> implements Serializable {

    @Inject
    private BancoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarBancos();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoBanco");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/banco";
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
