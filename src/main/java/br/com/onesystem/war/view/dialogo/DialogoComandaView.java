package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.builder.ItemDeComandaBV;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DialogoComandaView extends BasicMBImpl<Comanda, ItemDeComandaBV> implements Serializable {

    private Comanda comanda;
    private List<ItemDeComandaBV> ItensDeComanda;
    private ItemDeComandaBV itemDeComandaBV;
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
        comanda = (Comanda) SessionUtil.getObject("comanda", FacesContext.getCurrentInstance());
        tipoOperacao = (TipoOperacao) SessionUtil.getObject("tipoOperacao", FacesContext.getCurrentInstance());
    }

    private void populaCampos() {
        if (comanda != null) {
            comanda.getItensDeComanda().forEach((io) -> {
                try {
                    ItemDeComandaBV iobv = new ItemDeComandaBV(io);
                    List<SaldoDeEstoque> listaDeEstoque = serviceEstoque.buscaListaDeSaldoDe(io, comanda, tipoOperacao);
                    List<QuantidadeDeItemPorDeposito> lista = criaLista(listaDeEstoque, iobv);
                    iobv.setListaDeQuantidade(lista);
                    ItensDeComanda.add(iobv);
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

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoComanda", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof List) {
            List<QuantidadeDeItemPorDeposito> list = (List<QuantidadeDeItemPorDeposito>) event.getObject();
            itemDeComandaBV.setListaDeQuantidade((List<QuantidadeDeItemPorDeposito>) event.getObject());
        }
    }

    public void atribuiItemASessao(ItemDeComandaBV itemDeComanda) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.quantidadeLista.token");
        session.setAttribute("onesystem.quantidadeLista.token", itemDeComanda.getListaDeQuantidade());

        itemDeComandaBV = itemDeComanda;
        abrirJanelaQuantidade();
    }

    public List<QuantidadeDeItemPorDeposito> criaLista(List<SaldoDeEstoque> listaDeEstoque, ItemDeComandaBV itemDeComanda) {

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
        RequestContext.getCurrentInstance().execute("document.getElementById(\"tempDialog:tabs:ItensDeComandaComanda:0:exibeQuantidade-btn\").click();");
    }

    public void salvar() {
        try {
            List<ItemDeComandaBV> lista = preparaItens();
            removeDaSessao();
            RequestContext.getCurrentInstance().closeDialog(lista);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private List<ItemDeComandaBV> preparaItens() throws EDadoInvalidoException {
        List<ItemDeComandaBV> lista = ItensDeComanda.stream().filter(item -> item.getTotalListaDeQuantidade().compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList());
        for (ItemDeComandaBV ib : lista) {
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
        if (comanda != null) {
            return MoedaFormatter.format(comanda.getCotacao().getConta().getMoeda(), BigDecimal.ZERO);
        } else {
            return "";
        }
    }

    @Override
    public void limparJanela() {
        itemDeComandaBV = new ItemDeComandaBV();
        ItensDeComanda = new ArrayList<>();
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public List<ItemDeComandaBV> getItensDeComanda() {
        return ItensDeComanda;
    }

    public void setItensDeComanda(List<ItemDeComandaBV> ItensDeComanda) {
        this.ItensDeComanda = ItensDeComanda;
    }

    public ItemDeComandaBV getItemDeComandaBV() {
        return itemDeComandaBV;
    }

    public void setItemDeComandaBV(ItemDeComandaBV itemDeComandaBV) {
        this.itemDeComandaBV = itemDeComandaBV;
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
