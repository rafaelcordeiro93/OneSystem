package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ItemImagemDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemImagem;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.services.CalculadoraDePreco;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.WarningMessage;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.war.builder.PrecoDeItemBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.PrecoDeItemService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private Configuracao configuracao;
    private List<SaldoDeEstoque> estoqueLista;
    private BigDecimal estoqueTotal;
    private List<PrecoDeItem> precoAtual;
    private List<PrecoDeItem> precos;
    private List<ItemImagem> imagens;
    private boolean tab = true;
    private boolean renderBotoes = true;
    private ItemImagem itemImagem;

    @Inject
    private ConfiguracaoService serviceConfigurcao;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private PrecoDeItemService servicePrecoDeItem;

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

    public void addPreco() {
        try {
            validaMargem();
            PrecoDeItem novoRegistro = precoDeItemBV.construir();
            new AdicionaDAO<PrecoDeItem>().adiciona(novoRegistro);
            limparJanelaPreco();
            inicializaPrecos();
            InfoMessage.adicionado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void limparJanela() {
        e = new ItemBV();
        estoqueLista = new ArrayList<SaldoDeEstoque>();
        imagens = new ArrayList<>();
        limparJanelaPreco();
        tab = true;
        renderBotoes = true;
    }

    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = (Object) event.getObject();
            if (obj instanceof Item) {
                t = (Item) obj;
                e = new ItemBV(t);
                selecionaItem();
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
            } else if (obj instanceof ListaDePreco) {
                calculaPreco();
                precoDeItemBV.setListaDePreco((ListaDePreco) obj);
            }
        } catch (DadoInvalidoException die) {
            die.print();

        }
    }

    public void selecionaItem() throws DadoInvalidoException {
        if (e.getId() != null) {
            t = e.construirComID();
            inicializaDados();
            tab = false;
        }
    }

    public void calculaPreco() throws DadoInvalidoException {
        if (!precoPorMargem && e.getMargem() != null) {
            if (configuracao.getTipoDeFormacaoDePreco() != null && configuracao.getTipoDeCalculoDeCusto() != null) {
                CalculadoraDePreco calculadora = new CalculadoraDePreco(e.construirComID(), configuracao.getTipoDeCalculoDeCusto());
                precoDeItemBV.setValor(configuracao.getTipoDeFormacaoDePreco() == TipoDeFormacaoDePreco.MARKUP
                        ? calculadora.getPrecoMarkup() : calculadora.getPrecoMargemContribuicao());
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } else if (!precoPorMargem && e.getMargem() == null) {
            WarningMessage.print(new BundleUtil().getMessage("Defina_Margem_Para_Calcular_Preco"));
        }
    }

    private void validaMargem() throws EDadoInvalidoException {
        if (!precoPorMargem && e.getMargem() == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("margem_not_null"));
        }
    }

    private void inicializaDados() throws DadoInvalidoException {
        inicializaEstoque();
        inicializaPrecos();
    }

    private void inicializaImagens() {
        imagens = new ArrayList(new ItemImagemDAO().porItem(t).listaDeResultados());
    }

    private void inicializaPrecos() throws DadoInvalidoException {
        Item i = e.construirComID();
        precoDeItemBV.setItem(i);
        precoAtual = servicePrecoDeItem.buscaListaDePrecoAtual(i);
        precos = servicePrecoDeItem.buscaTodosPrecos(i);
    }

    public void onTabChange(TabChangeEvent event) {
        String str = event.getTab().getTitle();
        if (str == new BundleUtil().getLabel("Preco") || str == new BundleUtil().getLabel("Estoque") || str == new BundleUtil().getLabel("Imagens")) {
            renderBotoes = false;
            if (str == new BundleUtil().getLabel("Imagens")) {
                inicializaImagens();
            }
        } else {
            renderBotoes = true;
        }
    }

    private void inicializaEstoque() throws DadoInvalidoException {
        estoqueLista = serviceEstoque.buscaListaDeSaldoDeEstoque(e.construirComID(), null);
    }

    public void favoritarImagem(ItemImagem itemImagem) {
        try {
            ItemImagem imgDesfavoritada = imagens.stream().filter(ItemImagem::isFavorita).filter(i -> (!i.getId().equals(itemImagem.getId()))).findAny().get();
            itemImagem.setFavorita(true);
            imgDesfavoritada.setFavorita(false);
            new AtualizaDAO<>().atualiza(imgDesfavoritada);
            new AtualizaDAO<>().atualiza(itemImagem);
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
            new AdicionaDAO().adiciona(itemImagem);
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
                new AtualizaDAO<>().atualiza(imgFavoritada);
                imagens.set(imagens.indexOf(imgFavoritada), imgFavoritada);
            }
            new RemoveDAO<ItemImagem>().remove(itemImagem, itemImagem.getId());
            imagens.remove(itemImagem);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
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

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
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

}
