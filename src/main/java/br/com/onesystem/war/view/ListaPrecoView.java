package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ListaPreco;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ListaPrecoBV;
import br.com.onesystem.war.service.ListaPrecoService;
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
public class ListaPrecoView implements Serializable {

    private boolean panel;
    private ListaPrecoBV listaPreco;
    private ListaPreco listaPrecoSelecionada;
    private List<ListaPreco> listaPrecoLista;
    private List<ListaPreco> listaPrecosFiltradas;

    @ManagedProperty("#{listaPrecoService}")
    private ListaPrecoService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        listaPrecoLista = service.buscarListaPrecos();
    }

    public void add() {
        try {
            ListaPreco novoRegistro = listaPreco.construir();
            if (!validaListaPrecoExistente(novoRegistro)) {
                new AdicionaDAO<ListaPreco>().adiciona(novoRegistro);
                listaPrecoLista.add(novoRegistro);
                InfoMessage.print("¡ListaPreco '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la listaPreco!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            ListaPreco listaPrecoExistente = listaPreco.construirComID();
            if (listaPrecoExistente.getId() != null) {
                if (!validaListaPrecoExistente(listaPrecoExistente)) {
                    new AtualizaDAO<ListaPreco>(ListaPreco.class).atualiza(listaPrecoExistente);
                    listaPrecoLista.set(listaPrecoLista.indexOf(listaPrecoExistente),
                            listaPrecoExistente);
                    if (listaPrecosFiltradas != null && listaPrecosFiltradas.contains(listaPrecoExistente)) {
                        listaPrecosFiltradas.set(listaPrecosFiltradas.indexOf(listaPrecoExistente), listaPrecoExistente);
                    }
                    InfoMessage.print("¡ListaPreco '" + listaPrecoExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe la listaPreco!");
                }
            } else {
                throw new EDadoInvalidoException("!La listaPreco no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (listaPrecoLista != null && listaPrecoLista.contains(listaPrecoSelecionada)) {
                new RemoveDAO<ListaPreco>(ListaPreco.class).remove(listaPrecoSelecionada, listaPrecoSelecionada.getId());
                listaPrecoLista.remove(listaPrecoSelecionada);
                if (listaPrecosFiltradas != null && listaPrecosFiltradas.contains(listaPrecoSelecionada)) {
                    listaPrecosFiltradas.remove(listaPrecoSelecionada);
                }
                InfoMessage.print("ListaPreco '" + this.listaPreco.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaListaPrecoExistente(ListaPreco novoRegistro) {
        for (ListaPreco novaListaPreco : listaPrecoLista) {
            if (novoRegistro.getNome().equals(novaListaPreco.getNome())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        listaPreco = new ListaPrecoBV();
        listaPrecoSelecionada = new ListaPreco();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        listaPreco = new ListaPrecoBV(listaPrecoSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (listaPrecoSelecionada != null) {
            listaPreco = new ListaPrecoBV(listaPrecoSelecionada);
        }
    }

    public ListaPrecoBV getListaPreco() {
        return listaPreco;
    }

    public void setListaPreco(ListaPrecoBV listaPreco) {
        this.listaPreco = listaPreco;
    }

    public ListaPreco getListaPrecoSelecionada() {
        return listaPrecoSelecionada;
    }

    public void setListaPrecoSelecionada(ListaPreco listaPrecoSelecionada) {
        this.listaPrecoSelecionada = listaPrecoSelecionada;
    }

    public List<ListaPreco> getListaPrecoLista() {
        return listaPrecoLista;
    }

    public void setListaPrecoLista(List<ListaPreco> listaPrecoLista) {
        this.listaPrecoLista = listaPrecoLista;
    }

    public List<ListaPreco> getListaPrecosFiltradas() {
        return listaPrecosFiltradas;
    }

    public void setListaPrecosFiltradas(List<ListaPreco> listaPrecosFiltradas) {
        this.listaPrecosFiltradas = listaPrecosFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public ListaPrecoService getService() {
        return service;
    }

    public void setService(ListaPrecoService service) {
        this.service = service;
    }

}
