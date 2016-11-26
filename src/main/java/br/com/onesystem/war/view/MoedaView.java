package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.MoedaBV;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class MoedaView implements Serializable {

    private boolean panel;
    private MoedaBV moeda;
    private Moeda moedaSelecionada;
    private List<Moeda> moedaLista;
    private List<Moeda> moedasFiltradas;

    @ManagedProperty("#{moedaService}")
    private MoedaService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        moedaLista = service.buscarMoedas();
    }

    public void add() {
        try {
            Moeda novoRegistro = moeda.construir();
            if (!validaMoedaExistente(novoRegistro)) {
                new AdicionaDAO<Moeda>().adiciona(novoRegistro);
                moedaLista.add(novoRegistro);
                InfoMessage.print("¡Moneda '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la ciudad!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Moeda moedaExistente = moeda.construirComID();
            if (moedaExistente.getId() != null) {
                if (!validaMoedaExistente(moedaExistente)) {
                    new AtualizaDAO<Moeda>(Moeda.class).atualiza(moedaExistente);
                    moedaLista.set(moedaLista.indexOf(moedaExistente),
                            moedaExistente);
                    if (moedasFiltradas != null && moedasFiltradas.contains(moedaExistente)) {
                        moedasFiltradas.set(moedasFiltradas.indexOf(moedaExistente), moedaExistente);
                    }
                    InfoMessage.print("¡Moneda '" + moedaExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe la ciudad!");
                }
            } else {
                throw new EDadoInvalidoException("!La ciudad no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (moedaLista != null && moedaLista.contains(moedaSelecionada)) {
                new RemoveDAO<Moeda>(Moeda.class).remove(moedaSelecionada, moedaSelecionada.getId());
                moedaLista.remove(moedaSelecionada);
                if (moedasFiltradas != null && moedasFiltradas.contains(moedaSelecionada)) {
                    moedasFiltradas.remove(moedaSelecionada);
                }
                InfoMessage.print("Moneda '" + this.moeda.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaMoedaExistente(Moeda novoRegistro) {
        for (Moeda novaMoeda : moedaLista) {
            if (novoRegistro.getNome().equals(novaMoeda.getNome())
                    && novoRegistro.getSigla().equals(novaMoeda.getSigla())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        moeda = new MoedaBV();
        moedaSelecionada = new Moeda();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        moeda = new MoedaBV(moedaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public MoedaBV getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaBV moeda) {
        this.moeda = moeda;
    }

    public Moeda getMoedaSelecionada() {
        return moedaSelecionada;
    }

    public void setMoedaSelecionada(Moeda moedaSelecionada) {
        this.moedaSelecionada = moedaSelecionada;
    }

    public List<Moeda> getMoedaLista() {
        return moedaLista;
    }

    public void setMoedaLista(List<Moeda> moedaLista) {
        this.moedaLista = moedaLista;
    }

    public List<Moeda> getMoedasFiltradas() {
        return moedasFiltradas;
    }

    public void setMoedasFiltradas(List<Moeda> moedasFiltradas) {
        this.moedasFiltradas = moedasFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public MoedaService getService() {
        return service;
    }

    public void setService(MoedaService service) {
        this.service = service;
    }

}
