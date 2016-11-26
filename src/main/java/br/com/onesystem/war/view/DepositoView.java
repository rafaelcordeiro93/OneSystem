package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.DepositoBV;
import br.com.onesystem.war.service.DepositoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class DepositoView implements Serializable {

    private boolean panel;
    private DepositoBV deposito;
    private Deposito depositoSelecionada;
    private List<Deposito> depositoLista;
    private List<Deposito> depositosFiltradas;

    @ManagedProperty("#{depositoService}")
    private DepositoService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        depositoLista = service.buscarDepositos();
    }

    public void add() {
        try {
            Deposito novoRegistro = deposito.construir();
            if (!validaDepositoExistente(novoRegistro)) {
                new AdicionaDAO<Deposito>().adiciona(novoRegistro);
                depositoLista.add(novoRegistro);
                InfoMessage.print("¡Deposito '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la deposito!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Deposito depositoExistente = deposito.construirComID();
            if (depositoExistente.getId() != null) {
                if (!validaDepositoExistente(depositoExistente)) {
                    new AtualizaDAO<Deposito>(Deposito.class).atualiza(depositoExistente);
                    depositoLista.set(depositoLista.indexOf(depositoExistente),
                            depositoExistente);
                    if (depositosFiltradas != null && depositosFiltradas.contains(depositoExistente)) {
                        depositosFiltradas.set(depositosFiltradas.indexOf(depositoExistente), depositoExistente);
                    }
                    InfoMessage.print("¡Deposito '" + depositoExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe la deposito!");
                }
            } else {
                throw new EDadoInvalidoException("!El deposito no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (depositoLista != null && depositoLista.contains(depositoSelecionada)) {
                new RemoveDAO<Deposito>(Deposito.class).remove(depositoSelecionada, depositoSelecionada.getId());
                depositoLista.remove(depositoSelecionada);
                if (depositosFiltradas != null && depositosFiltradas.contains(depositoSelecionada)) {
                    depositosFiltradas.remove(depositoSelecionada);
                }
                InfoMessage.print("Deposito '" + this.deposito.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaDepositoExistente(Deposito novoRegistro) {
        for (Deposito novaDeposito : depositoLista) {
            if (novoRegistro.getNome().equals(novaDeposito.getNome())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        deposito = new DepositoBV();
        depositoSelecionada = new Deposito();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        deposito = new DepositoBV(depositoSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (depositoSelecionada != null) {
            deposito = new DepositoBV(depositoSelecionada);
        }
    }

    public DepositoBV getDeposito() {
        return deposito;
    }

    public void setDeposito(DepositoBV deposito) {
        this.deposito = deposito;
    }

    public Deposito getDepositoSelecionada() {
        return depositoSelecionada;
    }

    public void setDepositoSelecionada(Deposito depositoSelecionada) {
        this.depositoSelecionada = depositoSelecionada;
    }

    public List<Deposito> getDepositoLista() {
        return depositoLista;
    }

    public void setDepositoLista(List<Deposito> depositoLista) {
        this.depositoLista = depositoLista;
    }

    public List<Deposito> getDepositosFiltradas() {
        return depositosFiltradas;
    }

    public void setDepositosFiltradas(List<Deposito> depositosFiltradas) {
        this.depositosFiltradas = depositosFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public DepositoService getService() {
        return service;
    }

    public void setService(DepositoService service) {
        this.service = service;
    }

}
