package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoDeMargem;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.service.GrupoService;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.MarcaService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.PrecoDeItemBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ItemView implements Serializable {

    private ItemBV item;
    private Item itemSelecionada;
    private PrecoDeItemBV precoDeItemBV;
    private boolean precoPorMargem = true;
    private Configuracao configuracao;
    private List<SaldoDeEstoque> estoqueLista;
    private BigDecimal estoqueTotal;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService serviceConfigurcao;

    @ManagedProperty("#{estoqueService}")
    private EstoqueService serviceEstoque;

    @PostConstruct
    public void init() {
        limparJanela();        
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            Item novoRegistro = item.construir();
            new AdicionaDAO<Item>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addPreco() {
        try {
            PrecoDeItem novoRegistro = precoDeItemBV.construir();
            new AdicionaDAO<PrecoDeItem>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanelaPreco();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (itemSelecionada != null) {
                Item itemExistente = item.construirComID();
                new AtualizaDAO<Item>(Item.class).atualiza(itemExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (itemSelecionada != null) {
                new RemoveDAO<Item>(Item.class).remove(itemSelecionada, itemSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public List<TipoItem> getTipoItem() {
        return Arrays.asList(TipoItem.values());
    }

    public void limparJanela() {
        item = new ItemBV();
        itemSelecionada = null;
        estoqueLista = new ArrayList<SaldoDeEstoque>();
        limparJanelaPreco();
    }

    public void limparJanelaPreco() {
        precoDeItemBV = new PrecoDeItemBV();
    }

    public void selecionaItem(SelectEvent event) {
        itemSelecionada = (Item) event.getObject();
        estoqueLista = serviceEstoque.buscaListaDeSaldoDeEstoque(itemSelecionada);
        item = new ItemBV(itemSelecionada);
    }

    public void selecionaListaDePreco(SelectEvent event) {
        ListaDePreco listaDePreco = (ListaDePreco) event.getObject();
        precoDeItemBV.setListaDePreco(listaDePreco);
    }

    public void selecionaMargem(SelectEvent event) {
        GrupoDeMargem g = (GrupoDeMargem) event.getObject();
        item.setMargem(g);    
    }

    private void carregaEstoque() {
        estoqueLista = new EstoqueService().buscaListaDeSaldoDeEstoque(itemSelecionada);
    }

    public void desfazer() {
        if (itemSelecionada != null) {
            item = new ItemBV(itemSelecionada);
        }
    }

    public void selecionaGrupoFiscal(SelectEvent event) {
        GrupoFiscal grupo = (GrupoFiscal) event.getObject();
        item.setGrupoFiscal(grupo);
    }

    public void selecionaGrupo(SelectEvent e) {
        item.setGrupo((Grupo) e.getObject());
    }

    public void selecionaMarca(SelectEvent e) {
        item.setMarca((Marca) e.getObject());
    }

    public void selecionaUnidadeMedida(SelectEvent e) {
        item.setUnidadeMedida((UnidadeMedidaItem) e.getObject());
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

    public PrecoDeItemBV getPrecoDeItemBV() {
        return precoDeItemBV;
    }

    public void setPrecoDeItemBV(PrecoDeItemBV precoDeItemBV) {
        this.precoDeItemBV = precoDeItemBV;
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

    public boolean isPrecoPorMargem() {
        return precoPorMargem;
    }

    public void setPrecoPorMargem(boolean precoPorMargem) {
        this.precoPorMargem = precoPorMargem;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

}
