package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.MarcaDAO;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoMarcaView extends BasicCrudMBImpl<Marca> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private MarcaDAO dao;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.listaDeResultados(manager);
    }

    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoMarca");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/cadastros/marca";
    }

    @Override
    public List<Marca> complete(String query) {
        buscarDados();
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

}
