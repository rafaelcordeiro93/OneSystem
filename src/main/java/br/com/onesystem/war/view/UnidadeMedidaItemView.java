package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.PessoaBV;
import br.com.onesystem.war.builder.UnidadeMedidaItemBV;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
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
public class UnidadeMedidaItemView implements Serializable {

    private boolean panel;
    private UnidadeMedidaItemBV unidadeMedidaItem;
    private UnidadeMedidaItem unidadeMedidaItemSelecionada;
    private List<UnidadeMedidaItem> unidadeMedidaItemLista;
    private List<UnidadeMedidaItem> unidadeMedidaItensFiltrados;

    @ManagedProperty("#{unidadeMedidaItemService}")
    private UnidadeMedidaItemService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        unidadeMedidaItemLista = service.buscarUnidadeMedidaItens();
    }

    public void add() {
        try {
            UnidadeMedidaItem novoRegistro = unidadeMedidaItem.construir();
            unidadMedidaExiste(false);
            new AdicionaDAO<UnidadeMedidaItem>().adiciona(novoRegistro);
            unidadeMedidaItemLista.add(novoRegistro);
            InfoMessage.print("¡Unidad de Medida '" + novoRegistro.getNome() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            UnidadeMedidaItem unidadeMedidaItemExistente = unidadeMedidaItem.construirComID();
            unidadMedidaExiste(true);
            if (unidadeMedidaItemExistente.getId() != null) {
                new AtualizaDAO<UnidadeMedidaItem>(UnidadeMedidaItem.class).atualiza(unidadeMedidaItemExistente);
                unidadeMedidaItemLista.set(unidadeMedidaItemLista.indexOf(unidadeMedidaItemExistente),
                        unidadeMedidaItemExistente);
                if (unidadeMedidaItensFiltrados != null && unidadeMedidaItensFiltrados.contains(unidadeMedidaItemExistente)) {
                    unidadeMedidaItensFiltrados.set(unidadeMedidaItensFiltrados.indexOf(unidadeMedidaItemExistente), unidadeMedidaItemExistente);
                }
                InfoMessage.print("¡Unidad de Medida '" + unidadeMedidaItemExistente.getNome() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La unidad de medida no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (unidadeMedidaItemLista != null && unidadeMedidaItemLista.contains(unidadeMedidaItemSelecionada)) {
                new RemoveDAO<UnidadeMedidaItem>(UnidadeMedidaItem.class).remove(unidadeMedidaItemSelecionada, unidadeMedidaItemSelecionada.getId());
                unidadeMedidaItemLista.remove(unidadeMedidaItemSelecionada);
                if (unidadeMedidaItensFiltrados != null && unidadeMedidaItensFiltrados.contains(unidadeMedidaItemSelecionada)) {
                    unidadeMedidaItensFiltrados.remove(unidadeMedidaItemSelecionada);
                }
                InfoMessage.print("Unidad de Medida '" + this.unidadeMedidaItem.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void unidadMedidaExiste(boolean unidadMedidaExiste) throws DadoInvalidoException {
        if (unidadMedidaExiste) {
            for (UnidadeMedidaItem unidadMedidaLista : unidadeMedidaItemLista) {
                if (unidadMedidaLista.getSigla().equalsIgnoreCase(this.unidadeMedidaItem.getSigla())
                        && unidadMedidaLista.getId() != this.unidadeMedidaItem.getId()) {
                    throw new IDadoInvalidoException("¡Unidad Medida ya existe!");
                }
            }
        } else {
            for (UnidadeMedidaItem unidadMedidaLista : unidadeMedidaItemLista) {
                if (unidadMedidaLista.getSigla().equalsIgnoreCase(this.unidadeMedidaItem.getSigla())) {
                    throw new IDadoInvalidoException("¡Unidad Medida ya existe!");
                }
            }
        }
    }
    
    public void desfazer() {
        if (unidadeMedidaItemSelecionada != null) {
            unidadeMedidaItem = new UnidadeMedidaItemBV(unidadeMedidaItemSelecionada);
        }
    }

    public void limparJanela() {
        unidadeMedidaItem = new UnidadeMedidaItemBV();
        unidadeMedidaItemSelecionada = new UnidadeMedidaItem();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        unidadeMedidaItem = new UnidadeMedidaItemBV(unidadeMedidaItemSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public UnidadeMedidaItemBV getUnidadeMedidaItem() {
        return unidadeMedidaItem;
    }

    public void setUnidadeMedidaItem(UnidadeMedidaItemBV unidadeMedidaItem) {
        this.unidadeMedidaItem = unidadeMedidaItem;
    }

    public UnidadeMedidaItem getUnidadeMedidaItemSelecionada() {
        return unidadeMedidaItemSelecionada;
    }

    public void setUnidadeMedidaItemSelecionada(UnidadeMedidaItem unidadeMedidaItemSelecionada) {
        this.unidadeMedidaItemSelecionada = unidadeMedidaItemSelecionada;
    }

    public List<UnidadeMedidaItem> getUnidadeMedidaItemLista() {
        return unidadeMedidaItemLista;
    }

    public void setUnidadeMedidaItemLista(List<UnidadeMedidaItem> unidadeMedidaItemLista) {
        this.unidadeMedidaItemLista = unidadeMedidaItemLista;
    }

    public List<UnidadeMedidaItem> getUnidadeMedidaItensFiltrados() {
        return unidadeMedidaItensFiltrados;
    }

    public void setUnidadeMedidaItensFiltrados(List<UnidadeMedidaItem> unidadeMedidaItensFiltrados) {
        this.unidadeMedidaItensFiltrados = unidadeMedidaItensFiltrados;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public UnidadeMedidaItemService getService() {
        return service;
    }

    public void setService(UnidadeMedidaItemService service) {
        this.service = service;
    }

}
