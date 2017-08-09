package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.LancamentoBancarioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoLancamentoBancarioView extends BasicCrudMBImpl<LancamentoBancario> implements Serializable {

    @Inject
    private LancamentoBancarioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarLancamentoBancarios();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoLancamentoBancario");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaLancamentoBancario";
    }

    @Override
    public List<LancamentoBancario> complete(String query) {
        List<LancamentoBancario> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (LancamentoBancario c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public LancamentoBancarioService getService() {
        return service;
    }

    public void setService(LancamentoBancarioService service) {
        this.service = service;
    }
}
