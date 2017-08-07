package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.MarcaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SelecaoMarcaView extends BasicCrudMBImpl<Marca> implements Serializable {

    @Inject
    private MarcaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarMarcas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoMarca");
    }
    
    @Override
    public String abrirEdicao() {
        return "marca";
    }
    
     @Override
    public List<Marca> complete(String query) {
        List<Marca> listaFIltrada = new ArrayList<>();
        for (Marca b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                listaFIltrada.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Marca m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    listaFIltrada.add(m);
                }
            }
        }
        return listaFIltrada;
    }

    public MarcaService getService() {
        return service;
    }

    public void setService(MarcaService service) {
        this.service = service;
    }
}
