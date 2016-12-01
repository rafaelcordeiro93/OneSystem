package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.service.GrupoService;
import br.com.onesystem.war.service.IVAService;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.MarcaService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ItemView implements Serializable {

    private boolean panel;
    private ItemBV item;
    private Item itemSelecionada;
    private List<Item> itemLista;
    private List<Item> itemsFiltradas;
    private IVA ivaSelecionada;
    private List<IVA> ivaLista;
    private List<IVA> ivasFiltradas;
    private Grupo grupoSelecionado;
    private List<Grupo> grupoLista;
    private List<Grupo> gruposFiltrados;
    private Marca marcaSelecionada;
    private List<Marca> marcaLista;
    private List<Marca> marcasFiltradas;
    private UnidadeMedidaItem unidadeMedidaSelecionada;
    private List<UnidadeMedidaItem> unidadeMedidaLista;
    private List<UnidadeMedidaItem> unidadeMedidaFiltradas;
    private List<SaldoDeEstoque> estoqueLista;
    private BigDecimal estoqueTotal;
    

    @ManagedProperty("#{itemService}")
    private ItemService service;

    @ManagedProperty("#{ivaService}")
    private IVAService serviceIVA;

    @ManagedProperty("#{grupoService}")
    private GrupoService serviceGrupo;

    @ManagedProperty("#{marcaService}")
    private MarcaService serviceMarca;

    @ManagedProperty("#{unidadeMedidaItemService}")
    private UnidadeMedidaItemService serviceUnMedida;
    
    @ManagedProperty("#{estoqueService}")
    private EstoqueService serviceEstoque;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        itemLista = service.buscarItems();
        ivaLista = serviceIVA.buscarIVAs();
        grupoLista = serviceGrupo.buscarGrupos();
        marcaLista = serviceMarca.buscarMarcas();
        unidadeMedidaLista = serviceUnMedida.buscarUnidadeMedidaItens();
     
    }

    public void add() {
        try {
            Item novoRegistro = item.construir();
            if (!validaItemExistente(novoRegistro)) {
                new AdicionaDAO<Item>().adiciona(novoRegistro);
                itemLista.add(novoRegistro);
                InfoMessage.print("¡Item '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe el item!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Item itemExistente = item.construirComID();
            if (itemExistente.getId() != null) {
                if (!validaItemExistente(itemExistente)) {
                    new AtualizaDAO<Item>(Item.class).atualiza(itemExistente);
                    itemLista.set(itemLista.indexOf(itemExistente),
                            itemExistente);
                    if (itemsFiltradas != null && itemsFiltradas.contains(itemExistente)) {
                        itemsFiltradas.set(itemsFiltradas.indexOf(itemExistente), itemExistente);
                    }
                    InfoMessage.print("¡Item '" + itemExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe el item!");
                }
            } else {
                throw new EDadoInvalidoException("!El item no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (itemLista != null && itemLista.contains(itemSelecionada)) {
                new RemoveDAO<Item>(Item.class).remove(itemSelecionada, itemSelecionada.getId());
                itemLista.remove(itemSelecionada);
                if (itemsFiltradas != null && itemsFiltradas.contains(itemSelecionada)) {
                    itemsFiltradas.remove(itemSelecionada);
                }
                InfoMessage.print("Item '" + this.item.getNome() + "' eliminado con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    } 
    
    private boolean validaItemExistente(Item novoRegistro) {
        for (Item novaItem : itemLista) {
            if (novoRegistro.getNome().equals(novaItem.getNome())) {
                return true;
            }
        }
        return false;
    }

    public List<TipoItem> getTipoItem() {
        return Arrays.asList(TipoItem.values());
    }

    public void limparJanela() {
        item = new ItemBV();
        itemSelecionada = new Item();
        estoqueLista = new ArrayList<SaldoDeEstoque>();
    }
    
    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        item = new ItemBV(itemSelecionada);
        estoqueLista = new EstoqueService().buscaSaldoDeEstoque(itemSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }
    
      public void desfazer() {
        if (itemSelecionada != null) {
            item = new ItemBV(itemSelecionada);
        }
    }

    public void selecionaIVA() {
        item.setIva(ivaSelecionada);
    }

    public void selecionaGrupo() {
        item.setGrupo(grupoSelecionado);
    }

    public void selecionaMarca() {
        item.setMarca(marcaSelecionada);
    }

    public void selecionaUnidadeMedida() {
        item.setUnidadeMedida(unidadeMedidaSelecionada);
    }
    
    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public ItemBV getItem() {
        return item;
    }

    public void setItem(ItemBV item) {
        this.item = item;
    }

    public Item getItemSelecionada() {
        return itemSelecionada;
    }

    public void setItemSelecionada(Item itemSelecionada) {
        this.itemSelecionada = itemSelecionada;
    }

    public List<Item> getItemLista() {
        return itemLista;
    }

    public void setItemLista(List<Item> itemLista) {
        this.itemLista = itemLista;
    }

    public List<Item> getItemsFiltradas() {
        return itemsFiltradas;
    }

    public void setItemsFiltradas(List<Item> itemsFiltradas) {
        this.itemsFiltradas = itemsFiltradas;
    }

    public IVA getIvaSelecionada() {
        return ivaSelecionada;
    }

    public void setIvaSelecionada(IVA ivaSelecionada) {
        this.ivaSelecionada = ivaSelecionada;
    }

    public List<IVA> getIvaLista() {
        return ivaLista;
    }

    public void setIvaLista(List<IVA> ivaLista) {
        this.ivaLista = ivaLista;
    }

    public List<IVA> getIvasFiltradas() {
        return ivasFiltradas;
    }

    public void setIvasFiltradas(List<IVA> ivasFiltradas) {
        this.ivasFiltradas = ivasFiltradas;
    }

    public Grupo getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(Grupo grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

    public List<Grupo> getGrupoLista() {
        return grupoLista;
    }

    public void setGrupoLista(List<Grupo> grupoLista) {
        this.grupoLista = grupoLista;
    }

    public List<Grupo> getGruposFiltrados() {
        return gruposFiltrados;
    }

    public void setGruposFiltrados(List<Grupo> gruposFiltrados) {
        this.gruposFiltrados = gruposFiltrados;
    }

    public Marca getMarcaSelecionada() {
        return marcaSelecionada;
    }

    public void setMarcaSelecionada(Marca marcaSelecionada) {
        this.marcaSelecionada = marcaSelecionada;
    }

    public List<Marca> getMarcaLista() {
        return marcaLista;
    }

    public void setMarcaLista(List<Marca> marcaLista) {
        this.marcaLista = marcaLista;
    }

    public List<Marca> getMarcasFiltradas() {
        return marcasFiltradas;
    }

    public void setMarcasFiltradas(List<Marca> marcasFiltradas) {
        this.marcasFiltradas = marcasFiltradas;
    }

    public UnidadeMedidaItem getUnidadeMedidaSelecionada() {
        return unidadeMedidaSelecionada;
    }

    public void setUnidadeMedidaSelecionada(UnidadeMedidaItem unidadeMedidaSelecionada) {
        this.unidadeMedidaSelecionada = unidadeMedidaSelecionada;
    }

    public List<UnidadeMedidaItem> getUnidadeMedidaLista() {
        return unidadeMedidaLista;
    }

    public void setUnidadeMedidaLista(List<UnidadeMedidaItem> unidadeMedidaLista) {
        this.unidadeMedidaLista = unidadeMedidaLista;
    }

    public List<UnidadeMedidaItem> getUnidadeMedidaFiltradas() {
        return unidadeMedidaFiltradas;
    }

    public void setUnidadeMedidaFiltradas(List<UnidadeMedidaItem> unidadeMedidaFiltradas) {
        this.unidadeMedidaFiltradas = unidadeMedidaFiltradas;
    }

    public ItemService getService() {
        return service;
    }

    public void setService(ItemService service) {
        this.service = service;
    }

    public IVAService getServiceIVA() {
        return serviceIVA;
    }

    public void setServiceIVA(IVAService serviceIVA) {
        this.serviceIVA = serviceIVA;
    }

    public GrupoService getServiceGrupo() {
        return serviceGrupo;
    }

    public void setServiceGrupo(GrupoService serviceGrupo) {
        this.serviceGrupo = serviceGrupo;
    }

    public MarcaService getServiceMarca() {
        return serviceMarca;
    }

    public void setServiceMarca(MarcaService serviceMarca) {
        this.serviceMarca = serviceMarca;
    }

    public UnidadeMedidaItemService getServiceUnMedida() {
        return serviceUnMedida;
    }

    public void setServiceUnMedida(UnidadeMedidaItemService serviceUnMedida) {
        this.serviceUnMedida = serviceUnMedida;
        
    }

    public List<SaldoDeEstoque> getEstoqueLista() {
        return estoqueLista;
    }

    public void setEstoqueLista(List<SaldoDeEstoque> estoqueLista) {
        this.estoqueLista = estoqueLista;
    }

    public BigDecimal getEstoqueTotal() {
        return estoqueTotal;
    }

    public void setEstoqueTotal(BigDecimal estoqueTotal) {
        this.estoqueTotal = estoqueTotal;
    }


    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

 
    
    
}
