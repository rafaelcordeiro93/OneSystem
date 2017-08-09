package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.TransferenciaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoTransferenciaView extends BasicCrudMBImpl<Transferencia> implements Serializable {

    @Inject
    private TransferenciaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarTransferencias();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoTransferencia");
    }

    @Override
    public String abrirEdicao() {
        return "transferencia";
    }

    @Override
    public List<Transferencia> complete(String query) {
        List<Transferencia> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Transferencia c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public TransferenciaService getService() {
        return service;
    }

    public void setService(TransferenciaService service) {
        this.service = service;
    }
}
