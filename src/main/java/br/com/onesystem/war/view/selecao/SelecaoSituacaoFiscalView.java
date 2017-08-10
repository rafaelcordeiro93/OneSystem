package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.SituacaoFiscalService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoSituacaoFiscalView extends BasicCrudMBImpl<SituacaoFiscal> implements Serializable {

    @Inject
    private SituacaoFiscalService service;

    @PostConstruct
    public void init() {
        beans = service.buscarSituacoesFiscais();
    }

    public void abrirDialogo() {
        exibirNaTela("contabil/selecao/selecaoSituacaoFiscal");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/contabil/operacoes";
    }

    @Override
    public List<SituacaoFiscal> complete(String query) {
        List<SituacaoFiscal> listaFIltrada = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (SituacaoFiscal m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public List<SituacaoFiscal> getSituacoesOrdenadasPorSequencia() {
        List<SituacaoFiscal> list = new ArrayList<>(beans);
        list.sort(Comparator.comparing(SituacaoFiscal::getSequencia));
        return list;
    }

    public SituacaoFiscalService getService() {
        return service;
    }

    public void setService(SituacaoFiscalService service) {
        this.service = service;
    }
}
