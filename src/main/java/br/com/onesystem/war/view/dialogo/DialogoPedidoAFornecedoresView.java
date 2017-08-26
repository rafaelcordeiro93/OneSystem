package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.builder.ItemDePedidoBV;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoPedidoAFornecedoresView extends BasicMBImpl<PedidoAFornecedores, ItemDePedidoBV> implements Serializable {

    private PedidoAFornecedores pedido;
    private List<ItemDePedidoBV> ItensDePedido;
    private ItemDePedidoBV itemDePedidoBV;
    private TipoOperacao tipoOperacao;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private ConfiguracaoEstoqueService serviceConfiguracaoEstoque;

    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
            populaCampos();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void buscaDaSessao() throws FDadoInvalidoException {
        pedido = (PedidoAFornecedores) SessionUtil.getObject("pedidoAFornecedores", FacesContext.getCurrentInstance());
        tipoOperacao = (TipoOperacao) SessionUtil.getObject("tipoOperacao", FacesContext.getCurrentInstance());
    }

   
    private void populaCampos() {
        if (pedido != null) {
            pedido.getItensDePedido().forEach((io) -> {
                try {
                    ItemDePedidoBV iobv = new ItemDePedidoBV(io);
                    List<SaldoDeEstoque> listaDeEstoque = serviceEstoque.buscaListaDeSaldoDe(io, pedido, tipoOperacao);
                    List<QuantidadeDeItemPorDeposito> lista = criaLista(listaDeEstoque, iobv);
                    iobv.setListaDeQuantidade(lista);
                    ItensDePedido.add(iobv);
                } catch (DadoInvalidoException ex) {
                    ex.print();
                    ex.printConsole();
                    throw new RuntimeException(ex);
                }
            });
        }
    }


    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", "90%");
        opcoes.put("draggable", false);
        opcoes.put("height", 600);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoPedidoAFornecedores", opcoes, null);
    }

     @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof List) {
            List<QuantidadeDeItemPorDeposito> list = (List<QuantidadeDeItemPorDeposito>) event.getObject();
            itemDePedidoBV.setListaDeQuantidade((List<QuantidadeDeItemPorDeposito>) event.getObject());
        }
    }

    public void atribuiItemASessao(ItemDePedidoBV itemDePedido) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.quantidadeLista.token");
        session.setAttribute("onesystem.quantidadeLista.token", itemDePedido.getListaDeQuantidade());

        itemDePedidoBV = itemDePedido;
        abrirJanelaQuantidade();
    }

    public List<QuantidadeDeItemPorDeposito> criaLista(List<SaldoDeEstoque> listaDeEstoque, ItemDePedidoBV itemDePedidoAFornecedores) {
        List<QuantidadeDeItemPorDeposito> lista = new ArrayList<>();
        for (SaldoDeEstoque saldo : listaDeEstoque) {
            if (saldo.getDeposito().getId().equals(serviceConfiguracaoEstoque.buscar().getDepositoPadrao().getId())) {
                QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, BigDecimal.ZERO);
                lista.add(quantidadeDeItem);
                listaDeEstoque.remove(saldo);
                break;
            }
        }
        return lista;
    }

    private void abrirJanelaQuantidade() {
        RequestContext.getCurrentInstance().execute("document.getElementById(\"tempDialog:tabs:ItensDePedidoAFornecedores:0:exibeQuantidade-btn\").click();");
    }

    public void salvar() {
        try {
            List<ItemDePedidoBV> lista = preparaItens();
            removeDaSessao();
            RequestContext.getCurrentInstance().closeDialog(lista);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private List<ItemDePedidoBV> preparaItens() throws EDadoInvalidoException {
        List<ItemDePedidoBV> lista = ItensDePedido.stream().filter(item -> item.getTotalListaDeQuantidade().compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList());
        for (ItemDePedidoBV ib : lista) {
            if (ib.getComparaQuantidadeDevolucao() < 0) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Existem_quantidade_a_faturar_maior_que_saldo"));
            }
        }
        return lista;
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("nota", FacesContext.getCurrentInstance());
        SessionUtil.remove("tipoOperacao", FacesContext.getCurrentInstance());
    }

    public String getZero() {
        if (pedido != null) {
            return MoedaFormatter.format(pedido.getMoedaPadrao(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    @Override
    public void limparJanela() {
        itemDePedidoBV = new ItemDePedidoBV();
        ItensDePedido = new ArrayList<>();
    }

    public PedidoAFornecedores getPedidoAFornecedores() {
        return pedido;
    }

    public void setPedidoAFornecedores(PedidoAFornecedores pedido) {
        this.pedido = pedido;
    }

    public List<ItemDePedidoBV> getItensDePedido() {
        return ItensDePedido;
    }

    public void setItensDePedido(List<ItemDePedidoBV> ItensDePedido) {
        this.ItensDePedido = ItensDePedido;
    }

    public ItemDePedidoBV getItemDePedidoBV() {
        return itemDePedidoBV;
    }

    public void setItemDePedidoBV(ItemDePedidoBV itemDePedidoAFornecedoresBV) {
        this.itemDePedidoBV = itemDePedidoAFornecedoresBV;
    }

    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public ConfiguracaoEstoqueService getServiceConfiguracaoEstoque() {
        return serviceConfiguracaoEstoque;
    }

    public void setServiceConfiguracaoEstoque(ConfiguracaoEstoqueService serviceConfiguracaoEstoque) {
        this.serviceConfiguracaoEstoque = serviceConfiguracaoEstoque;
    }

}
