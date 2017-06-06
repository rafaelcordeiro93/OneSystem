package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.GrupoFinanceiroBV;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
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
            if (grupoFinanceiroSelecionado != null) {
                GrupoFinanceiro grupoFinanceiroExistente = grupoFinanceiro.construirComID();
                new AtualizaDAO<GrupoFinanceiro>().atualiza(grupoFinanceiroExistente);
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
                new RemoveDAO<GrupoFinanceiro>().remove(grupoFinanceiroSelecionado, grupoFinanceiroSelecionado.getId());
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

    public void buscaPorId() {
        Long id = grupoFinanceiro.getId();
        if (id != null) {
            try {
                GrupoFinanceiroDAO dao = new GrupoFinanceiroDAO();
                GrupoFinanceiro c = dao.buscarGrupoFinanceiros().porId(id).resultado();
                grupoFinanceiroSelecionado = c;
                grupoFinanceiro = new GrupoFinanceiroBV(grupoFinanceiroSelecionado);
            } catch (DadoInvalidoException die) {
                limparJanela();
                grupoFinanceiro.setId(id);
                die.print();
            }
        }
    }

    public List<NaturezaFinanceira> getNaturezasFinanceiras() {
        return Arrays.asList(NaturezaFinanceira.values());
    }

    public List<ClassificacaoFinanceira> getClassificacaoFinanceira() {
        return Arrays.asList(ClassificacaoFinanceira.values());
    }

    public void limparJanela() {
        grupoFinanceiro = new GrupoFinanceiroBV();
        grupoFinanceiroSelecionado = null;
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
