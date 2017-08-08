package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.DepositoBancarioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoDepositoBancarioView extends BasicCrudMBImpl<DepositoBancario> implements Serializable {

    @Inject
    private DepositoBancarioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDepositoBancarios();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoDepositoBancario");
    }

    @Override
    public String abrirEdicao() {
        return "depositoBancario";
    }

    @Override
    public List<DepositoBancario> complete(String query) {
        List<DepositoBancario> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (DepositoBancario c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public DepositoBancarioService getService() {
        return service;
    }

    public void setService(DepositoBancarioService service) {
        this.service = service;
    }
}
