package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.war.service.OrcamentoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoOrcamentoAprovadoView extends BasicCrudMBImpl<Orcamento> implements Serializable {

    @Inject
    private OrcamentoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarOrcamentosNo(EstadoDeOrcamento.APROVADO);
    }    
    
    @Override
    public void abrirDialogo() {  
        exibirNaTela("selecaoOrcamentoAprovado");
    }

    @Override
    public String abrirEdicao() {
        return "orcamento";
    }

    @Override
    public List<Orcamento> complete(String query) {
        List<Orcamento> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Orcamento c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public OrcamentoService getService() {
        return service;
    }

    public void setService(OrcamentoService service) {
        this.service = service;
    }
}
