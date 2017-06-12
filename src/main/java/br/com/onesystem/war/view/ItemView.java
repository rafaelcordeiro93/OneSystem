package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
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
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ItemView extends BasicMBImpl<Item, ItemBV> implements Serializable {

    private PrecoDeItemBV precoDeItemBV;
    private boolean precoPorMargem = true;
    private Configuracao configuracao;
    private List<SaldoDeEstoque> estoqueLista;
    private BigDecimal estoqueTotal;
    private List<PrecoDeItem> precoAtual;
    private List<PrecoDeItem> precos;
    private boolean tab = true;

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
        limparJanelaPreco();
        tab = true;
    }

      
    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = (Object) event.getObject();
            if (obj instanceof Item) {
                e = new ItemBV((Item) obj);
                inicializaDados();
                tab = false;
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
                if (e.getMargem() != null) {
                    calculaPreco();
                }
                precoDeItemBV.setListaDePreco((ListaDePreco) obj);
            }
        } catch (DadoInvalidoException die) {
            die.print();

        }
    }

    private void calculaPreco() throws DadoInvalidoException {
        if (configuracao.getTipoDeFormacaoDePreco() != null && configuracao.getTipoDeCalculoDeCusto() != null) {
            CalculadoraDePreco calculadora = new CalculadoraDePreco(e.construirComID(), configuracao.getTipoDeCalculoDeCusto());
            precoDeItemBV.setValor(configuracao.getTipoDeFormacaoDePreco() == TipoDeFormacaoDePreco.MARKUP
                    ? calculadora.getPrecoMarkup() : calculadora.getPrecoMargemContribuicao());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
        }
    }

    private void inicializaDados() throws DadoInvalidoException {
        inicializaEstoque();
        inicializaPrecos();
    }

    private void validaMargem() throws EDadoInvalidoException {
        if (precoPorMargem && e.getMargem() == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("margem_not_null"));
        }
    }

    private void inicializaPrecos() throws DadoInvalidoException {
        Item i = e.construirComID();
        precoDeItemBV.setItem(i);
        precoAtual = servicePrecoDeItem.buscaListaDePrecoAtual(i);
        precos = servicePrecoDeItem.buscaTodosPrecos(i);
    }

    private void inicializaEstoque() throws DadoInvalidoException {
        estoqueLista = serviceEstoque.buscaListaDeSaldoDeEstoque(e.construirComID(), null);
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

}
