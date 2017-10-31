package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Baixa;
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
public class SelecaoBaixaView extends BasicCrudMBImpl<Baixa> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private ArmazemDeRegistros<Baixa> armazem;

    @PostConstruct
    public void init() {
        buscarDados();
    }

    public void buscarDados() {
        beans = armazem.daClasse(Baixa.class, manager).listaTodosOsRegistros();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("financeiro/selecao/selecaoBaixa");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/financeiro/baixa";
    }

    @Override
    public List<Baixa> complete(String query) {
        buscarDados();
        List<Baixa> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (Baixa c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

}
