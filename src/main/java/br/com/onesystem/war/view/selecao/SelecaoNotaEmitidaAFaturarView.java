package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@ViewScoped
public class SelecaoNotaEmitidaAFaturarView extends BasicCrudMBImpl<NotaEmitida> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private NotaEmitidaDAO dao;
    private Pessoa pessoa;

    @PostConstruct
    public void init() {
        buscaPessoa();
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.porAFaturarMaiorZero().porSemFaturaEmitida().porPessoa(pessoa).listaDeResultados(manager);
    }

    public void buscaPessoa() {
        try {
            pessoa = (Pessoa) SessionUtil.getObject("pessoaFaturaEmitida", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("vendas/selecao/selecaoNotaEmitidaAFaturar");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/vendas/notaEmitida";
    }

    @Override
    public List<NotaEmitida> complete(String query) {
        buscarDados();
        List<NotaEmitida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (NotaEmitida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public NotaEmitidaDAO getDao() {
        return dao;
    }

    public void setDao(NotaEmitidaDAO dao) {
        this.dao = dao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
