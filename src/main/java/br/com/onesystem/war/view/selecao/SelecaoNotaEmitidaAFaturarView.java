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
import javax.inject.Inject;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class SelecaoNotaEmitidaAFaturarView extends BasicCrudMBImpl<NotaEmitida> implements Serializable {

    @Inject
    private NotaEmitidaDAO dao;
    private Pessoa pessoa;

    @PostConstruct
    public void init() {
        buscaPessoa();
        beans = dao.porAFaturarMaiorZero().porSemFaturaEmitida().porPessoa(pessoa).listaDeResultados();
    }

    public void buscaPessoa(){
        try {
            pessoa = (Pessoa) SessionUtil.getObject("pessoaFaturaEmitida", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoNotaEmitidaAFaturar");
    }

    @Override
    public String abrirEdicao() {
        return "notaEmitida";
    }

    @Override
    public List<NotaEmitida> complete(String query) {
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
