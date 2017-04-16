package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.CidadeService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoCidadeView extends BasicCrudMBImpl<Cidade> implements Serializable {

    @Inject
    private CidadeService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCidades();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCidade");
    }
    
    @Override
    public String abrirEdicao() {
        return "cidade";
    }
    
     @Override
    public List<Cidade> complete(String query) {
        List<Cidade> listaFIltrada = new ArrayList<>();
        for (Cidade b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Cidade m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public CidadeService getService() {
        return service;
    }

    public void setService(CidadeService service) {
        this.service = service;
    }
}
