package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoFinanceiroBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GrupoFinanceiroView implements Serializable {

    private GrupoFinanceiroBV grupoFinanceiro;
    private GrupoFinanceiro grupoFinanceiroSelecionado;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            GrupoFinanceiro novoRegistro = grupoFinanceiro.construir();
            new AdicionaDAO<GrupoFinanceiro>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            GrupoFinanceiro grupoFinanceiroExistente = grupoFinanceiro.construirComID();
            if (grupoFinanceiroExistente.getId() != null) {

                new AtualizaDAO<GrupoFinanceiro>(GrupoFinanceiro.class).atualiza(grupoFinanceiroExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La grupoFinanceiro no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (grupoFinanceiroSelecionado != null) {
                new RemoveDAO<GrupoFinanceiro>(GrupoFinanceiro.class).remove(grupoFinanceiroSelecionado, grupoFinanceiroSelecionado.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean grupoFinanceiroExiste(GrupoFinanceiro novoRegistro) {
        List<GrupoFinanceiro> lista = new GrupoFinanceiroDAO().buscarGrupoFinanceiros().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void selecionaGrupoFinanceiro(SelectEvent e) {
        GrupoFinanceiro r = (GrupoFinanceiro) e.getObject();
        grupoFinanceiro = new GrupoFinanceiroBV(r);
        grupoFinanceiroSelecionado = r;
    }

    public List<NaturezaFinanceira> getNaturezasFinanceiras() {
        return Arrays.asList(NaturezaFinanceira.values());
    }

    public List<ClassificacaoFinanceira> getClassificacaoFinanceira() {
        return Arrays.asList(ClassificacaoFinanceira.values());
    }

    public void limparJanela() {
        grupoFinanceiro = new GrupoFinanceiroBV();
        grupoFinanceiroSelecionado = new GrupoFinanceiro();
    }

    public void desfazer() {
        if (grupoFinanceiroSelecionado != null) {
            grupoFinanceiro = new GrupoFinanceiroBV(grupoFinanceiroSelecionado);
        }
    }

    public GrupoFinanceiroBV getGrupoFinanceiro() {
        return grupoFinanceiro;
    }

    public void setGrupoFinanceiro(GrupoFinanceiroBV grupoFinanceiro) {
        this.grupoFinanceiro = grupoFinanceiro;
    }

    public GrupoFinanceiro getGrupoFinanceiroSelecionado() {
        return grupoFinanceiroSelecionado;
    }

    public void setGrupoFinanceiroSelecionado(GrupoFinanceiro grupoFinanceiroSelecionado) {
        this.grupoFinanceiroSelecionado = grupoFinanceiroSelecionado;
    }

}
