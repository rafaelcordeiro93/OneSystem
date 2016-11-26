package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.DespesaBV;
import br.com.onesystem.war.service.DespesaService;
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
public class DespesaView implements Serializable {

    private boolean panel;
    private DespesaBV despesa;
    private Despesa despesaSelecionada;
    private List<Despesa> despesaLista;
    private List<Despesa> despesasFiltradas;

    @ManagedProperty("#{despesaService}")
    private DespesaService service;
    
    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        despesaLista = service.buscarDespesas();
    }

    public void add() {
        try {
            Despesa novoRegistro = despesa.construir();
            new AdicionaDAO<Despesa>().adiciona(novoRegistro);
            despesaLista.add(novoRegistro);
            InfoMessage.print("¡Despesa '" + novoRegistro.getId() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Despesa despesaExistente = despesa.construirComID();
            if (despesaExistente.getId() != null) {
                new AtualizaDAO<Despesa>(Despesa.class).atualiza(despesaExistente);
                despesaLista.set(despesaLista.indexOf(despesaSelecionada),
                        despesaExistente);
                if (despesasFiltradas != null && despesasFiltradas.contains(despesaExistente)) {
                    despesasFiltradas.set(despesasFiltradas.indexOf(despesaExistente), despesaExistente);
                }
                InfoMessage.print("¡Despesa '" + despesaExistente.getId() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La despesa no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (despesaLista != null && despesaLista.contains(despesaSelecionada)) {
                new RemoveDAO<Despesa>(Despesa.class).remove(despesaSelecionada, despesaSelecionada.getId());
                despesaLista.remove(despesaSelecionada);
                if (despesasFiltradas != null && despesasFiltradas.contains(despesaSelecionada)) {
                    despesasFiltradas.remove(despesaSelecionada);
                }
                InfoMessage.print("Despesa '" + this.despesa.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        despesa = new DespesaBV();
        despesaSelecionada = new Despesa();
    }

    public void selecionaGrupoFinanceiro(SelectEvent event) {
        GrupoFinanceiro grupoFinanceiroSelecionado = (GrupoFinanceiro) event.getObject();
        despesa.setGrupoFinanceiro(grupoFinanceiroSelecionado);
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        despesa = new DespesaBV(despesaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (despesaSelecionada != null) {
            despesa = new DespesaBV(despesaSelecionada);
        }
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
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

    public List<Despesa> getDespesaLista() {
        return despesaLista;
    }

    public void setDespesaLista(List<Despesa> despesaLista) {
        this.despesaLista = despesaLista;
    }

    public List<Despesa> getDespesasFiltradas() {
        return despesasFiltradas;
    }

    public void setDespesasFiltradas(List<Despesa> despesasFiltradas) {
        this.despesasFiltradas = despesasFiltradas;
    }

    public DespesaService getService() {
        return service;
    }

    public void setService(DespesaService service) {
        this.service = service;
    }
  
}
