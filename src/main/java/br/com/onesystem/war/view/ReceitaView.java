package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ReceitaBV;
import br.com.onesystem.war.service.ReceitaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.service.GrupoFinanceiroService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ReceitaView implements Serializable {

    private boolean panel;
    private ReceitaBV receita;
    private Receita receitaSelecionada;
    private List<Receita> receitaLista;
    private List<Receita> receitasFiltradas;

    @ManagedProperty("#{receitaService}")
    private ReceitaService service;
    
    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        receitaLista = service.buscarReceitas();
    }

    public void add() {
        try {
            Receita novoRegistro = receita.construir();
            new AdicionaDAO<Receita>().adiciona(novoRegistro);
            receitaLista.add(novoRegistro);
            InfoMessage.print("¡Receita '" + novoRegistro.getId() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Receita receitaExistente = receita.construirComID();
            if (receitaExistente.getId() != null) {
                new AtualizaDAO<Receita>(Receita.class).atualiza(receitaExistente);
                receitaLista.set(receitaLista.indexOf(receitaSelecionada),
                        receitaExistente);
                if (receitasFiltradas != null && receitasFiltradas.contains(receitaExistente)) {
                    receitasFiltradas.set(receitasFiltradas.indexOf(receitaExistente), receitaExistente);
                }
                InfoMessage.print("¡Receita '" + receitaExistente.getId() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La receita no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (receitaLista != null && receitaLista.contains(receitaSelecionada)) {
                new RemoveDAO<Receita>(Receita.class).remove(receitaSelecionada, receitaSelecionada.getId());
                receitaLista.remove(receitaSelecionada);
                if (receitasFiltradas != null && receitasFiltradas.contains(receitaSelecionada)) {
                    receitasFiltradas.remove(receitaSelecionada);
                }
                InfoMessage.print("Receita '" + this.receita.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        receita = new ReceitaBV();
        receitaSelecionada = new Receita();
    }

    public void selecionaGrupoFinanceiro(SelectEvent event) {
        GrupoFinanceiro grupoFinanceiroSelecionado = (GrupoFinanceiro) event.getObject();
        receita.setGrupoFinanceiro(grupoFinanceiroSelecionado);
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        receita = new ReceitaBV(receitaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (receitaSelecionada != null) {
            receita = new ReceitaBV(receitaSelecionada);
        }
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
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

    public List<Receita> getReceitaLista() {
        return receitaLista;
    }

    public void setReceitaLista(List<Receita> receitaLista) {
        this.receitaLista = receitaLista;
    }

    public List<Receita> getReceitasFiltradas() {
        return receitasFiltradas;
    }

    public void setReceitasFiltradas(List<Receita> receitasFiltradas) {
        this.receitasFiltradas = receitasFiltradas;
    }

    public ReceitaService getService() {
        return service;
    }

    public void setService(ReceitaService service) {
        this.service = service;
    }
  
}
