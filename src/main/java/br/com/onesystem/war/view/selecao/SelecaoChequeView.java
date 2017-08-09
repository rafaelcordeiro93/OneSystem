package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ChequeService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoChequeView extends BasicCrudMBImpl<Cheque> implements Serializable {

    @Inject
    private ChequeService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCheques();
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoCheque");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/cheque";
    }
    
     @Override
    public List<Cheque> complete(String query) {
        List<Cheque> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Cheque m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public ChequeService getService() {
        return service;
    }

    public void setService(ChequeService service) {
        this.service = service;
    }
}
