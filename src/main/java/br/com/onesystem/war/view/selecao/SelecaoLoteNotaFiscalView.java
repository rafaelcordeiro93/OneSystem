package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.LoteNotaFiscalService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoLoteNotaFiscalView extends BasicCrudMBImpl<LoteNotaFiscal> implements Serializable {

    @Inject
    private LoteNotaFiscalService service;

    @PostConstruct
    public void init() {
        beans = service.buscarLoteNotaFiscais();
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoLoteNotaFiscal");
    }
    
    @Override
    public String abrirEdicao() {
        return "/menu/contabil/loteNotaFiscal";
    }
    
    @Override
    public List<LoteNotaFiscal> complete(String query) {
        List<LoteNotaFiscal> moedasFIltradas = new ArrayList<>();
        for (LoteNotaFiscal m : beans) {
            if (StringUtils.startsWithIgnoreCase(m.getNome(), query)) {
                moedasFIltradas.add(m);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (LoteNotaFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    moedasFIltradas.add(m);
                }
            }
        }
        return moedasFIltradas;
    }


    public LoteNotaFiscalService getService() {
        return service;
    }

    public void setService(LoteNotaFiscalService service) {
        this.service = service;
    }
}
