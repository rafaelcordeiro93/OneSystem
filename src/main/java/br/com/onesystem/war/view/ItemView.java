package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemImagem;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.services.CalculadoraDePreco;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.WarningMessage;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.war.builder.LoteItemBV;
import br.com.onesystem.war.builder.PrecoDeItemBV;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.PrecoDeItemService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

@Named
@ViewScoped //javax.faces.view.ViewScoped;
public class ItemView extends BasicMBImpl<Item, ItemBV> implements Serializable {

    private PrecoDeItemBV precoDeItemBV;
    private boolean precoPorMargem = false;
    private List<SaldoDeEstoque> estoqueLista;
    private BigDecimal estoqueTotal;
    private List<PrecoDeItem> precoAtual;
    private List<PrecoDeItem> precos;
    private List<ItemImagem> imagens;
    private boolean tab = true;
    private boolean renderBotoes = true;
    private ItemImagem itemImagem;
    private LoteItemBV loteDeItemBV;
    private Model<LoteItem> loteItemSelecionado;
    private ModelList<LoteItem> listaLoteItem;
    private Deposito deposito;

    @Inject
    private Configuracao configuracao;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private PrecoDeItemService servicePrecoDeItem;

    @Inject
    private ItemService itemService;

    @Inject
    private AdicionaDAO<PrecoDeItem> adicionaPrecoDAO;

    @Inject
    private AdicionaDAO<Estoque> adicionaEstoqueDAO;

    @Inject
    private AdicionaDAO<ItemImagem> adicionaImagemDAO;

    @Inject
    private RemoveDAO<ItemImagem> removeImagemDAO;

    @Inject
    private RemoveDAO<LoteItem> removeLoteItemDAO;

    @Inject
    private AtualizaDAO<ItemImagem> atualizaImagemDAO;

    @Inject
    private BundleUtil bundle;

    @Inject
    private CalculadoraDePreco calculadoraDePreco;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(bundle.getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            Item f = e.construir();
            listaLoteItem.getList().forEach((n) -> {
                f.adiciona(n);
            });
            addNoBanco(f);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void update() {
        try {
            Item f = e.construirComID();
            List<LoteItem> removidos = listaLoteItem.getRemovidos().stream().filter(m -> ((LoteItem) m.getObject()).getId() != null).map(m -> (LoteItem) m.getObject()).collect(Collectors.toList());
            removidos.forEach(c -> f.remove(c));
            listaLoteItem.getList().forEach((n) -> {
                f.atualiza(n);
            });
            updateNoBanco(f);
            for (LoteItem c : removidos) {
                removeLoteItemDAO.remove(c, c.getId());
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addPreco() {
        try {
            validaMargem();
            PrecoDeItem novoRegistro = precoDeItemBV.construir();
            adicionaPrecoDAO.adiciona(novoRegistro);
            limparJanelaPreco();
            inicializaPrecos();
            InfoMessage.adicionado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void limparJanela() {
        e = new ItemBV();
        t = null;
        estoqueLista = new ArrayList<SaldoDeEstoque>();
        imagens = new ArrayList<>();
        limparJanelaPreco();
        deposito = null;
        tab = true;
        renderBotoes = true;
        listaLoteItem = new ModelList<>();

    }

    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = (Object) event.getObject();
            if (obj instanceof Item) {
                e = new ItemBV((Item) obj);
                selecionaItem();
                limparLoteItem();
            } else if (obj instanceof GrupoFiscal) {
                e.setGrupoFiscal((GrupoFiscal) obj);
            } else if (obj instanceof Grupo) {
                e.setGrupo((Grupo) obj);
            } else if (obj instanceof Marca) {
                e.setMarca((Marca) obj);
            } else if (obj instanceof Margem) {
                e.setMargem((Margem) obj);
            } else if (obj instanceof UnidadeMedidaItem) {
                e.setUnidadeDeMedida((UnidadeMedidaItem) obj);
            } else if (obj instanceof Deposito) {
                deposito = (Deposito) obj;
            } else if (obj instanceof ListaDePreco) {
                if (precoDeItemBV == null) {
                    limparJanelaPreco();
                }
                precoDeItemBV.setListaDePreco((ListaDePreco) obj);
                calculaPreco();
            }
        } catch (DadoInvalidoException die) {
            die.print();

        }
    }

    public void selecionaItem() throws DadoInvalidoException {
        if (e.getId() != null) {
            t = e.construirComID();
            inicializaDados();
            listaLoteItem = new ModelList<LoteItem>(e.getLoteItem());
            tab = false;
        }
    }

    public void calculaPreco() throws DadoInvalidoException {
        if (!precoPorMargem && e.getMargem() != null) {
            if (configuracao.getTipoDeFormacaoDePreco() != null && configuracao.getTipoDeCalculoDeCusto() != null) {
                calculadoraDePreco.calcula(e.construirComID(), configuracao.getTipoDeCalculoDeCusto());
                precoDeItemBV.setValor(configuracao.getTipoDeFormacaoDePreco() == TipoDeFormacaoDePreco.MARKUP
                        ? calculadoraDePreco.getPrecoMarkup() : calculadoraDePreco.getPrecoMargemContribuicao());
            } else {
                throw new EDadoInvalidoException(bundle.getMessage("Configuracao_nao_definida"));
            }
        } else if (!precoPorMargem && e.getMargem() == null) {
            WarningMessage.print(bundle.getMessage("Defina_Margem_Para_Calcular_Preco"));
        }
    }

    public void validaMargemView() {
        try {
            validaMargem();
            // Só calcula o preço se a lista de preço já estiver informada.
            if (precoDeItemBV.getListaDePreco() != null) {
                calculaPreco();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaMargem() throws DadoInvalidoException {
        if (!precoPorMargem && e.getMargem() == null) {
            throw new ADadoInvalidoException(bundle.getMessage("Defina_Margem_Para_Calcular_Preco"));
        }
    }

    private void inicializaDados() throws DadoInvalidoException {
        inicializaEstoque();
        inicializaPrecos();
    }

    public void adicionaDepositoParaItem() {
        try {
            // Adiciona um estoque com saldo zero para que o depósito seja relacionado ao item.
            SaldoDeEstoque saldo = estoqueLista.stream().filter(s -> s.getDeposito().equals(deposito)).findAny().orElse(null);
            if (saldo == null) {
                OperacaoDeEstoque operacaoDeEstoque = configuracaoEstoque.getAjusteDeEstoquePadrao().getOperacaoDeEstoque().get(0);
                Estoque estoque = new EstoqueBuilder().comQuantidade(BigDecimal.ZERO).comDeposito(deposito).
                        comEmissao(new Date()).comOperacaoDeEstoque(operacaoDeEstoque)
                        .comItem(t).construir();
                adicionaEstoqueDAO.adiciona(estoque);
                inicializaEstoque();
                InfoMessage.adicionado();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void inicializaImagens() {
        imagens = new ArrayList(itemService.buscarImagemDo(t));
    }

    private void inicializaPrecos() throws DadoInvalidoException {
        Item i = e.construirComID();
        precoDeItemBV.setItem(i);
        precoAtual = servicePrecoDeItem.buscaListaDePrecoAtual(i);
        precos = servicePrecoDeItem.buscaTodosPrecos(i);
    }

    public void onTabChange(TabChangeEvent event) {
        String str = event.getTab().getTitle();
        if (str == bundle.getLabel("Preco") || str == bundle.getLabel("Estoque") || str == bundle.getLabel("Imagens")) {
            renderBotoes = false;
            if (str == bundle.getLabel("Imagens")) {
                inicializaImagens();
            }
        } else {
            renderBotoes = true;
        }
    }

    private void inicializaEstoque() throws DadoInvalidoException {
        estoqueLista = serviceEstoque.buscaListaDeSaldoDeEstoqueEmTodosDepositos(e.construirComID(), new Date());
    }

    public void favoritarImagem(ItemImagem itemImagem) {
        try {
            ItemImagem imgDesfavoritada = imagens.stream().filter(ItemImagem::isFavorita).filter(i -> (!i.getId().equals(itemImagem.getId()))).findAny().get();
            itemImagem.setFavorita(true);
            imgDesfavoritada.setFavorita(false);
            atualizaImagemDAO.atualiza(imgDesfavoritada);
            atualizaImagemDAO.atualiza(itemImagem);
            imagens.set(imagens.indexOf(imgDesfavoritada), imgDesfavoritada);
            imagens.set(imagens.indexOf(itemImagem), itemImagem);
            imagens.forEach(System.out::println);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void uploadImagens(FileUploadEvent event) {
        try {
            String fileName = event.getFile().getFileName();
            byte[] contents = event.getFile().getContents();
            String contentType = event.getFile().getContentType();
            boolean favorita = false;
            if (imagens.isEmpty()) {
                favorita = true;
            }
            ItemImagem itemImagem = new ItemImagem(null, fileName, contentType, contents, e.construirComID(), favorita);
            adicionaImagemDAO.adiciona(itemImagem);
            imagens.add(itemImagem);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void excluirImagem() {
        try {
            if (itemImagem.isFavorita() && imagens.size() > 1) {
                ItemImagem imgFavoritada = imagens.stream().filter(i -> (!i.getId().equals(itemImagem.getId()))).findFirst().get();
                imgFavoritada.setFavorita(true);
                atualizaImagemDAO.atualiza(imgFavoritada);
                imagens.set(imagens.indexOf(imgFavoritada), imgFavoritada);
            }
            removeImagemDAO.remove(itemImagem, itemImagem.getId());
            imagens.remove(itemImagem);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addLoteItem() {
        try {
            listaLoteItem.add(loteDeItemBV.construir());
            limparLoteItem();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void updateLoteItem() {
        try {
            if (loteDeItemBV.getId() == null) {
                loteItemSelecionado.setObject(loteDeItemBV.construir());
            } else {
                loteItemSelecionado.setObject(loteDeItemBV.construirComID());
            }
            listaLoteItem.set(loteItemSelecionado);
            limparLoteItem();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removeLoteItem() {
        listaLoteItem.remove(loteItemSelecionado);
        limparLoteItem();
    }

    public void selecionaLoteItem(SelectEvent event) {
        loteItemSelecionado = (Model<LoteItem>) event.getObject();
        loteDeItemBV = new LoteItemBV(loteItemSelecionado.getObject());;
    }

    public void limparLoteItem() {
        loteDeItemBV = new LoteItemBV();
        loteItemSelecionado = null;
        if (t != null) {
            loteDeItemBV.setItem(t);
        }
    }

    public List<DetalhamentoDeItem> getDetalhamentoDeItem() {
        return Arrays.asList(DetalhamentoDeItem.values());
    }

    public List<TipoItem> getTipoItem() {
        return Arrays.asList(TipoItem.values());
    }

    public void limparJanelaPreco() {
        precoDeItemBV = new PrecoDeItemBV();
    }

    public ItemBV getItem() {
        return e;
    }

    public void setItem(ItemBV e) {
        this.e = e;
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

    public List<ItemImagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<ItemImagem> imagens) {
        this.imagens = imagens;
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

    public List<PrecoDeItem> getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(List<PrecoDeItem> precoAtual) {
        this.precoAtual = precoAtual;
    }

    public boolean isRenderBotoes() {
        return renderBotoes;
    }

    public void setRenderBotoes(boolean renderBotoes) {
        this.renderBotoes = renderBotoes;
    }

    public boolean isTab() {
        return tab;
    }

    public void setTab(boolean tab) {
        this.tab = tab;
    }

    public List<PrecoDeItem> getPrecos() {
        return precos;
    }

    public void setPrecos(List<PrecoDeItem> precos) {
        this.precos = precos;
    }

    public PrecoDeItemService getServicePrecoDeItem() {
        return servicePrecoDeItem;
    }

    public void setServicePrecoDeItem(PrecoDeItemService servicePrecoDeItem) {
        this.servicePrecoDeItem = servicePrecoDeItem;
    }

    public ItemImagem getItemImagem() {
        return itemImagem;
    }

    public void setItemImagem(ItemImagem itemImagem) {
        this.itemImagem = itemImagem;
    }

    public LoteItemBV getLoteDeItemBV() {
        return loteDeItemBV;
    }

    public void setLoteDeItemBV(LoteItemBV loteDeItemBV) {
        this.loteDeItemBV = loteDeItemBV;
    }

    public Model<LoteItem> getLoteItemSelecionado() {
        return loteItemSelecionado;
    }

    public void setLoteItemSelecionado(Model<LoteItem> loteItemSelecionado) {
        this.loteItemSelecionado = loteItemSelecionado;
    }

    public ModelList<LoteItem> getListaLoteItem() {
        return listaLoteItem;
    }

    public void setListaLoteItem(ModelList<LoteItem> listaLoteItem) {
        this.listaLoteItem = listaLoteItem;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

}
