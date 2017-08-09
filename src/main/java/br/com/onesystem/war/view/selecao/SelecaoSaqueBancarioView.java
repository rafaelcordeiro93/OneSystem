package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.SaqueBancarioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoSaqueBancarioView extends BasicCrudMBImpl<SaqueBancario> implements Serializable {

    @Inject
    private SaqueBancarioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarSaqueBancarios();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoSaqueBancario");
    }

    @Override
    public String abrirEdicao() {
        return "saqueBancario";
    }

    @Override
    public List<SaqueBancario> complete(String query) {
        List<SaqueBancario> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (SaqueBancario c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public SaqueBancarioService getService() {
        return service;
    }

    public void setService(SaqueBancarioService service) {
        this.service = service;
    }
}
