package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.war.builder.ItemDeNotaBV;
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
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoNotaEmitidaView extends BasicMBImpl<NotaEmitida, ItemDeNotaBV> implements Serializable {

    private NotaEmitida notaEmitida;
    private List<ItemDeNotaBV> ItensDeNota;
    private ItemDeNotaBV itemDeNotaBV;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private ConfiguracaoEstoqueService serviceConfiguracaoEstoque;

    @PostConstruct
    public void init() {
        limparJanela();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        notaEmitida = (NotaEmitida) session.getAttribute("onesystem.nota.token");
        if (notaEmitida != null) {
            notaEmitida.getItens().forEach((io) -> {
                ItemDeNotaBV iobv = new ItemDeNotaBV(io);
                List<SaldoDeEstoque> listaDeEstoque = serviceEstoque.buscaListaDeSaldoDeDevolucao(iobv.getItem(), notaEmitida);
                List<QuantidadeDeItemPorDeposito> lista = criaLista(listaDeEstoque, iobv);
                iobv.setListaDeQuantidade(lista);
                ItensDeNota.add(iobv);
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
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%"); 
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoDevolucaoCliente", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof List) {
            List<QuantidadeDeItemPorDeposito> list = (List<QuantidadeDeItemPorDeposito>) event.getObject();
            itemDeNotaBV.setListaDeQuantidade((List<QuantidadeDeItemPorDeposito>) event.getObject());
        }
    }

    public void atribuiItemASessao(ItemDeNotaBV itemDeNota) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.quantidadeLista.token");
        session.setAttribute("onesystem.quantidadeLista.token", itemDeNota.getListaDeQuantidade());

        itemDeNotaBV = itemDeNota;
        abrirJanelaQuantidade();
    }

    public List<QuantidadeDeItemPorDeposito> criaLista(List<SaldoDeEstoque> listaDeEstoque, ItemDeNotaBV itemDeNota) {
        BigDecimal quantidade = itemDeNota.getQuantidade();

        List<QuantidadeDeItemPorDeposito> lista = new ArrayList<QuantidadeDeItemPorDeposito>();
        for (SaldoDeEstoque saldo : listaDeEstoque) {
         
            if (saldo.getDeposito().getId().equals(serviceConfiguracaoEstoque.buscar().getDepositoPadrao().getId())) {
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
                QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, itemDeNota.getQuantidade());
                lista.add(quantidadeDeItem);
            }
        }
        return lista;
    }

    private void abrirJanelaQuantidade() {
        RequestContext.getCurrentInstance().execute("document.getElementById(\"tempDialog:tabs:ItensDeNotaNotaEmitida:0:exibeQuantidade-btn\").click();");
    }

    public void salvar() {
        for (ItemDeNotaBV ib : ItensDeNota) {
            if (ib.getComparaQuantidadeDevolucao() < 0) {
                ErrorMessage.print(new BundleUtil().getMessage("Existe_Quantidade_A_Devolver_Maior_Que_Quantidade"));
                return;
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.nota.token");
        RequestContext.getCurrentInstance().closeDialog(ItensDeNota);
    }

    @Override
    public void limparJanela() {
        itemDeNotaBV = new ItemDeNotaBV();
        ItensDeNota = new ArrayList<>();
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public List<ItemDeNotaBV> getItensDeNota() {
        return ItensDeNota;
    }

    public void setItensDeNota(List<ItemDeNotaBV> ItensDeNota) {
        this.ItensDeNota = ItensDeNota;
    }

    public ItemDeNotaBV getItemDeNotaBV() {
        return itemDeNotaBV;
    }

    public void setItemDeNotaBV(ItemDeNotaBV itemDeNotaBV) {
        this.itemDeNotaBV = itemDeNotaBV;
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
