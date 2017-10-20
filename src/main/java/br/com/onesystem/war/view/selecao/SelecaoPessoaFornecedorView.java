package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PessoaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class SelecaoPessoaFornecedorView extends BasicCrudMBImpl<Pessoa> implements Serializable {

    @Inject
    private PessoaService service;

    @PostConstruct
    public void init() {
        buscarDados();
        beans = service.buscarFornecedores();
    }

    private void buscarDados() {
        beans = service.buscarFornecedores();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("arquivo/selecao/selecaoPessoaFornecedor");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/arquivo/pessoa";
    }

    @Override
    public List<Pessoa> complete(String query) {
        buscarDados();
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

    public PessoaService getService() {
        return service;
    }

    public void setService(PessoaService service) {
        this.service = service;
    }

    public String getIcon() {
        return "fa-user-o";
    }

}
