package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ReceitaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.ReceitaBV;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ReceitaView implements Serializable {

    private ReceitaBV receita;
    private Receita receitaSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();

    }

    public void add() {
        try {
            Receita novoRegistro = receita.construir();
            new AdicionaDAO<Receita>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Receita receitaExistente = receita.construirComID();
            if (receitaExistente.getId() != null) {
                new AtualizaDAO<Receita>().atualiza(receitaExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("receita_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (receitaSelecionada != null) {
                new RemoveDAO<Receita>().remove(receitaSelecionada, receitaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaReceita(SelectEvent e) {
        Receita r = (Receita) e.getObject();
        receita = new ReceitaBV(r);
        receitaSelecionada = r;
    }

    public void buscaPorId() {
        Long id = receita.getId();
        if (id != null) {
            try {
                ReceitaDAO dao = new ReceitaDAO();
                Receita c = dao.buscarReceitaW().porId(id).resultado();
                receitaSelecionada = c;
                receita = new ReceitaBV(receitaSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                receita.setId(id);
                die.print();
            }
        }
    }

    public void limparJanela() {
        receita = new ReceitaBV();
        receitaSelecionada = new Receita();
    }

    public void selecionaGrupoFinanceiro(SelectEvent event) {
        receita.setGrupoFinanceiro((GrupoFinanceiro) event.getObject());
    }

    public void desfazer() {
        if (receitaSelecionada != null) {
            receita = new ReceitaBV(receitaSelecionada);
        }
    }

    public ReceitaBV getReceita() {
        return receita;
    }

    public void setReceita(ReceitaBV receita) {
        this.receita = receita;
    }

    public Receita getReceitaSelecionada() {
        return receitaSelecionada;
    }

    public void setReceitaSelecionada(Receita receitaSelecionada) {
        this.receitaSelecionada = receitaSelecionada;
    }

}
