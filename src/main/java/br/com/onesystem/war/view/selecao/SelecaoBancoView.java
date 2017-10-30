package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Banco;
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
public class SelecaoBancoView extends BasicCrudMBImpl<Banco> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ArmazemDeRegistros<Banco> armazem;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = armazem.daClasse(Banco.class, manager).listaTodosOsRegistros();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoBanco");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/cadastros/banco";
    }

    @Override
    public List<Banco> complete(String query) {
        buscarDados();

        List<Banco> bancosFIltrados = new ArrayList<>();
        for (Banco b : beans) {
            if (StringUtils.startsWithIgnoreCase(b.getNome(), query)) {
                bancosFIltrados.add(b);
            }
        }
        if (!StringUtils.containsLetter(query)) {
            for (Banco m : beans) {
                if (StringUtils.startsWithIgnoreCase(m.getId().toString(), query)) {
                    bancosFIltrados.add(m);
                }
            }
        }
        return bancosFIltrados;
    }

}
