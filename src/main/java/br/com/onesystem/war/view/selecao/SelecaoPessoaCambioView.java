package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoPessoaCambioView extends BasicCrudMBImpl<Pessoa> implements Serializable {

    @Inject
    private ConfiguracaoCambioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPessoas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoPessoaCambio");
    }
    
    @Override
    public String abrirEdicao() {
        return "";
    }
    
    @Override
    public List<Pessoa> complete(String query) {
        List<Pessoa> listaFIltrada = new ArrayList<>();
        for (Pessoa b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Pessoa m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public ConfiguracaoCambioService getService() {
        return service;
    }

    public void setService(ConfiguracaoCambioService service) {
        this.service = service;
    }
}
