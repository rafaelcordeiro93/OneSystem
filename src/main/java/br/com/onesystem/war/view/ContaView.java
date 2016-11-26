package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContaBV;
import br.com.onesystem.war.service.ContaService;
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
public class ContaView implements Serializable {

    private boolean panel;
    private ContaBV conta;
    private Conta contaSelecionada;
    private List<Conta> contaLista;
    private List<Conta> recepcoesFiltradas;

    @ManagedProperty("#{contaService}")
    private ContaService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        contaLista = service.buscarContas();
    }

    public void add() {
        try {
            Conta novoRegistro = conta.construir();
            new AdicionaDAO<Conta>().adiciona(novoRegistro);
            contaLista.add(novoRegistro);
            InfoMessage.print("¡Conta '" + novoRegistro.getId() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Conta contaExistente = conta.construirComID();
            if (contaExistente.getId() != null) {
                new AtualizaDAO<Conta>(Conta.class).atualiza(contaExistente);
                contaLista.set(contaLista.indexOf(contaExistente),
                        contaExistente);
                if (recepcoesFiltradas != null && recepcoesFiltradas.contains(contaExistente)) {
                    recepcoesFiltradas.set(recepcoesFiltradas.indexOf(contaExistente), contaExistente);
                }
                InfoMessage.print("¡Conta '" + contaExistente.getId() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La conta no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (contaLista != null && contaLista.contains(contaSelecionada)) {
                new RemoveDAO<Conta>(Conta.class).remove(contaSelecionada, contaSelecionada.getId());
                contaLista.remove(contaSelecionada);
                if (recepcoesFiltradas != null && recepcoesFiltradas.contains(contaSelecionada)) {
                    recepcoesFiltradas.remove(contaSelecionada);
                }
                InfoMessage.print("Conta '" + this.conta.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        conta = new ContaBV();
        contaSelecionada = new Conta();
    }

    public void selecionarBanco(SelectEvent event) {
        Banco banco = (Banco) event.getObject();
        conta.setBanco(banco);
    }
    
    public void selecionarMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
        conta.setMoeda(moeda);
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        conta = new ContaBV(contaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (contaSelecionada != null) {
            conta = new ContaBV(contaSelecionada);
        }
    }

    public ContaBV getConta() {
        return conta;
    }

    public void setConta(ContaBV conta) {
        this.conta = conta;
    }

    public Conta getContaSelecionada() {
        return contaSelecionada;
    }

    public void setContaSelecionada(Conta contaSelecionada) {
        this.contaSelecionada = contaSelecionada;
    }

    public List<Conta> getContaLista() {
        return contaLista;
    }

    public void setContaLista(List<Conta> contaLista) {
        this.contaLista = contaLista;
    }

    public List<Conta> getContasFiltradas() {
        return recepcoesFiltradas;
    }

    public void setContasFiltradas(List<Conta> recepcoesFiltradas) {
        this.recepcoesFiltradas = recepcoesFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public ContaService getService() {
        return service;
    }

    public void setService(ContaService service) {
        this.service = service;
    }

    public List<Conta> getRecepcoesFiltradas() {
        return recepcoesFiltradas;
    }

    public void setRecepcoesFiltradas(List<Conta> recepcoesFiltradas) {
        this.recepcoesFiltradas = recepcoesFiltradas;
    }
}
