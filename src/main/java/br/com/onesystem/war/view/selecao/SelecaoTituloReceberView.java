package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.TituloService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoTituloReceberView extends BasicCrudMBImpl<Titulo> implements Serializable {

    @Inject
    private TituloService service;

    @PostConstruct
    public void init() {
        beans = service.buscarTitulosAReceber();
    }

    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoTituloReceber");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/consultar/consultaCobranca";
    }
 
    @Override
    public List<Titulo> complete(String query) {
        List<Titulo> titulosFiltrados = new ArrayList<>();
        if (!StringUtils.containsLetter(query)) {
            for (Titulo t : beans) {
                if (StringUtils.startsWithIgnoreCase(t.getId().toString(), query)) {
                    titulosFiltrados.add(t);
                }
            }
        }
        return titulosFiltrados;
    }

    public TituloService getService() {
        return service;
    }

    public void setService(TituloService service) {
        this.service = service;
    }
}
