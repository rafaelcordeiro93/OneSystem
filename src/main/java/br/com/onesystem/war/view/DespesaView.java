package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.DespesaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.DespesaBV;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DespesaView implements Serializable {

    private DespesaBV despesa;
    private Despesa despesaSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Despesa novoRegistro = despesa.construir();
            new AdicionaDAO<Despesa>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Despesa despesaExistente = despesa.construirComID();
            if (despesaExistente.getId() != null) {
                new AtualizaDAO<Despesa>().atualiza(despesaExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("despesa_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (despesaSelecionada != null) {
                new RemoveDAO<Despesa>().remove(despesaSelecionada, despesaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaDespesa(SelectEvent e) {
        Despesa d = (Despesa) e.getObject();
        despesa = new DespesaBV(d);
        despesaSelecionada = d;
    }

    public void buscaPorId() {
        Long id = despesa.getId();
        if (id != null) {
            try {
                DespesaDAO dao = new DespesaDAO();
                Despesa c = dao.buscarDespesaW().porId(id).resultado();
                despesaSelecionada = c;
                despesa = new DespesaBV(despesaSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                despesa.setId(id);
                die.print();
            }
        }
    }

    public void limparJanela() {
        despesa = new DespesaBV();
        despesaSelecionada = null;
    }

    public void selecionaGrupoFinanceiro(SelectEvent event) {
        GrupoFinanceiro grupoFinanceiroSelecionado = (GrupoFinanceiro) event.getObject();
        despesa.setGrupoFinanceiro(grupoFinanceiroSelecionado);
    }

    public void desfazer() {
        if (despesaSelecionada != null) {
            despesa = new DespesaBV(despesaSelecionada);
        }
    }

    public DespesaBV getDespesa() {
        return despesa;
    }

    public void setDespesa(DespesaBV despesa) {
        this.despesa = despesa;
    }

    public Despesa getDespesaSelecionada() {
        return despesaSelecionada;
    }

    public void setDespesaSelecionada(Despesa despesaSelecionada) {
        this.despesaSelecionada = despesaSelecionada;
    }

}
