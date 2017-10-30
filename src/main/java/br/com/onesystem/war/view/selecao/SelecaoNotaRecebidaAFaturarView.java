package br.com.onesystem.war.view.selecao;

import br.com.onesystem.dao.NotaRecebidaDAO;
import br.com.onesystem.domain.NotaRecebida;
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
public class SelecaoNotaRecebidaAFaturarView extends BasicCrudMBImpl<NotaRecebida> implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private NotaRecebidaDAO dao;

    @PostConstruct
    public void init() {
        buscaPessoa();
        buscarDados();
    }

    public void buscarDados() {
        beans = dao.porAFaturarMaiorZero().porSemFaturaRecebida().porPessoa(buscaPessoa()).listaDeResultados(manager);
    }

    public Pessoa buscaPessoa() {
        try {
            return (Pessoa) SessionUtil.getObject("pessoaFaturaRecebida", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException die) {
            die.print();
        }
        return null;
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("estoque/selecao/selecaoNotaRecebidaAFaturar");
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/notaRecebida";
    }

    @Override
    public List<NotaRecebida> complete(String query) {
        buscarDados();
        List<NotaRecebida> contasFIltradas = new ArrayList<>();

        if (!StringUtils.containsLetter(query)) {
            for (NotaRecebida c : beans) {
                if (StringUtils.startsWithIgnoreCase(c.getId().toString(), query)) {
                    contasFIltradas.add(c);
                }
            }
        }
        return contasFIltradas;
    }

    public NotaRecebidaDAO getDao() {
        return dao;
    }

    public void setDao(NotaRecebidaDAO dao) {
        this.dao = dao;
    }
}
