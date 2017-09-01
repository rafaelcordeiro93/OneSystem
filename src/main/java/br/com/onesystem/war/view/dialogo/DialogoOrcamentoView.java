package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.war.builder.ItemOrcadoBV;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoOrcamentoView extends BasicMBImpl<Orcamento, ItemOrcadoBV> implements Serializable {

    private Orcamento orcamento;
    private List<ItemOrcadoBV> itensOrcados;
    private ItemOrcadoBV itemOrcadoBV;
    private boolean criarDepositos = false;
    private ConfiguracaoEstoque configuracaoEstoque;

    @Inject
    private ConfiguracaoEstoqueService confEstoqueService;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private ConfiguracaoEstoqueService serviceConfiguracaoEstoque;

    @PostConstruct
    public void init() {
        limparJanela();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        orcamento = (Orcamento) session.getAttribute("onesystem.orcamento.token");
        if (orcamento != null) {
            orcamento.getItensOrcados().forEach((io) -> {
                ItemOrcadoBV iobv = new ItemOrcadoBV(io);
                List<SaldoDeEstoque> listaDeEstoque = serviceEstoque.buscaListaDeSaldoDeEstoque(iobv.getItem(), null);
                List<QuantidadeDeItemPorDeposito> lista = criaLista(listaDeEstoque, iobv.getQuantidade());
                iobv.setQuantidadePorDeposito(lista);
                iobv.setFaturar(iobv.getQuantidadeAFaturar());
                itensOrcados.add(iobv);
            });
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", true);
        opcoes.put("width", 900);
        opcoes.put("draggable", true);
        opcoes.put("height", 600);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoOrcamento", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof List) {
            criarDepositos = true;
            List<QuantidadeDeItemPorDeposito> list = (List<QuantidadeDeItemPorDeposito>) event.getObject();
            itemOrcadoBV.setQuantidadePorDeposito((List<QuantidadeDeItemPorDeposito>) event.getObject());
            itemOrcadoBV.setFaturar(itemOrcadoBV.getQuantidadeAFaturar());
            itensOrcados.set(itensOrcados.indexOf(itemOrcadoBV), itemOrcadoBV);
          //  RequestContext.getCurrentInstance().update("tempDialog");
//            itensOrcados.forEach((io) -> {
//                try {
//                    List<QuantidadeDeItemPorDeposito> list = (List<QuantidadeDeItemPorDeposito>) event.getObject();
//                    ItemOrcadoBV iobv = new ItemOrcadoBV(io.construirComId());
//                    iobv.setQuantidadePorDeposito((List<QuantidadeDeItemPorDeposito>) event.getObject());
//                    iobv.setFaturar(iobv.getQuantidadeAFaturar());
//                    if (itemOrcadoBV.equals(io)) {
//                        itensOrcados.set(itensOrcados.indexOf(io), iobv);
//                    }
//                } catch (DadoInvalidoException ex) {
//                    Logger.getLogger(DialogoOrcamentoView.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });

        }
    }

    public void atribuiItemASessao(ItemOrcadoBV itemOrcado) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.quantidadeLista.token");
        session.setAttribute("onesystem.quantidadeLista.token", itemOrcado.getQuantidadePorDeposito());
        itemOrcadoBV = itemOrcado;
        abrirJanelaQuantidade();
    }

    public List<QuantidadeDeItemPorDeposito> criaLista(List<SaldoDeEstoque> listaDeEstoque, BigDecimal qtd) {
        BigDecimal quantidade = qtd;

        List<QuantidadeDeItemPorDeposito> lista = new ArrayList<QuantidadeDeItemPorDeposito>();
        for (SaldoDeEstoque saldo : listaDeEstoque) {
            if (saldo.getDeposito().getId() == serviceConfiguracaoEstoque.buscar().getDepositoPadrao().getId()) {
                QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, BigDecimal.ZERO);
                if (saldo.getSaldo().compareTo(quantidade) >= 0) {
                    quantidadeDeItem.setQuantidade(quantidade);
                    quantidade = BigDecimal.ZERO;
                } else {
                    quantidadeDeItem.setQuantidade(saldo.getSaldo());
                    quantidade = quantidade.subtract(saldo.getSaldo());
                }
                lista.add(quantidadeDeItem);
                listaDeEstoque.remove(saldo);
                break;
            }
        }
        if (quantidade.compareTo(BigDecimal.ZERO) > 0) {
            for (SaldoDeEstoque saldo : listaDeEstoque) {
                QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, qtd);
                lista.add(quantidadeDeItem);
            }
        }
        return lista;
    }

    private void abrirJanelaQuantidade() {
        RequestContext.getCurrentInstance().execute("document.getElementById(\"tempDialog:tabs:itensOrcadosOrcamento:0:exibeQuantidade-btn\").click();");
    }

    public void salvar() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.orcamento.token");
        RequestContext.getCurrentInstance().closeDialog(itensOrcados);
    }

    public void geraListaDeEstoquePadrao() {
        try {
            if (!criarDepositos) {
                List<SaldoDeEstoque> listaDeEstoque = serviceEstoque.buscaListaDeSaldoDeEstoque(itemOrcadoBV.getItem(), null);
                itemOrcadoBV.setQuantidadePorDeposito((Arrays.asList(new QuantidadeDeItemPorDeposito(null, new SaldoDeEstoque(null, configuracaoEstoque.getDepositoPadrao(), null), itemOrcadoBV.getFaturar()))));
            } else {
                criarDepositos = false;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void selecionaNoFocus(ItemOrcadoBV io) {
        itemOrcadoBV = io;
    }

    @Override
    public void limparJanela() {
        itemOrcadoBV = new ItemOrcadoBV();
        itensOrcados = new ArrayList<>();
        configuracaoEstoque = confEstoqueService.buscar();
        criarDepositos = false;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public List<ItemOrcadoBV> getItensOrcados() {
        return itensOrcados;
    }

    public void setItensOrcados(List<ItemOrcadoBV> itensOrcados) {
        this.itensOrcados = itensOrcados;
    }

    public ItemOrcadoBV getItemOrcadoBV() {
        return itemOrcadoBV;
    }

    public void setItemOrcadoBV(ItemOrcadoBV itemOrcadoBV) {
        this.itemOrcadoBV = itemOrcadoBV;
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
