/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.PedidoAFornecedoresBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaPedidoAFornecedoresView extends BasicMBImpl<PedidoAFornecedores, PedidoAFornecedoresBV> implements Serializable {

    private ModelList<ParcelaDePedido> listaDeParcela;
    private Model<ItemDePedido> itemSelecionado;
    private ModelList<ItemDePedido> listaDeItens;
    private Model<ItemDePedidoCancelado> itemCanceladoSelecionado;
    private ModelList<ItemDePedidoCancelado> listaDeItensCancelados;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void limparJanela() {
        try {
            e = new PedidoAFornecedoresBV();
            e.setMoeda(e.getMoedaPadrao());
            t = null;
            itemCanceladoSelecionado = null;
            itemSelecionado = null;
            listaDeParcela = new ModelList<>();
            listaDeItens = new ModelList<>();
            listaDeItensCancelados = new ModelList<>();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof PedidoAFornecedores) {
            e = new PedidoAFornecedoresBV((PedidoAFornecedores) obj);
            listaDeItens = new ModelList<>(e.getItens());
            listaDeParcela = new ModelList<>(e.getParcelaDePedido());
            listaDeItensCancelados = new ModelList<>(e.getItensCancelados());
        } else if (obj instanceof Model) {
            itemCanceladoSelecionado = (Model) obj;
            listaDeItensCancelados.set(itemCanceladoSelecionado);
            RequestContext.getCurrentInstance().update("conteudo:tabView:itensCanceladosDePedido");
        } else if (obj instanceof ItemDePedidoCancelado) {
            listaDeItensCancelados.add((ItemDePedidoCancelado) obj);
            RequestContext.getCurrentInstance().update("conteudo:tabView:itensCanceladosDePedido");
        }

    }

    @Override
    public void update() {
        try {

            PedidoAFornecedores p = e.construirComID();
            List<ItemDePedidoCancelado> itemRemovidas = listaDeItensCancelados.getRemovidos().stream().filter(m -> ((ItemDePedidoCancelado) m.getObject()).getId() != null).map(m -> (ItemDePedidoCancelado) m.getObject()).collect(Collectors.toList());
            itemRemovidas.forEach(n -> p.remove(n));

            listaDeItensCancelados.getList().forEach((n) -> {
                p.atualiza(n);
            });
            new AtualizaDAO<>().atualiza(p);
            for (ItemDePedidoCancelado v : itemRemovidas) {
                new RemoveDAO<>().remove(v, v.getId());
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
        InfoMessage.atualizado();
        RequestContext.getCurrentInstance().update("conteudo");
        limparJanela();
    }

    public void selecionaItem(SelectEvent event) {
        itemSelecionado = (Model<ItemDePedido>) event.getObject();
    }

    public void selecionaItemCancelado(SelectEvent event) {
        itemCanceladoSelecionado = (Model<ItemDePedidoCancelado>) event.getObject();
    }

    public void addItemCancelado() {
        try {
            if (itemSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_um_item"));
            }
            SessionUtil.put((ItemDePedido) itemSelecionado.getObject(), "itemDePedido", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void updateItemCancelado() {
        try {
            if (itemCanceladoSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_um_item"));
            }
            SessionUtil.put(itemCanceladoSelecionado, "model", FacesContext.getCurrentInstance());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removerItemCancelado() {
        try {
            if (itemCanceladoSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_um_item"));
            }
            listaDeItensCancelados.remove(itemCanceladoSelecionado);
            itemCanceladoSelecionado = null;
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public String getTotalItens() throws EDadoInvalidoException {
        if (listaDeItens != null) {
            BigDecimal valor = listaDeItens.getList().stream().map(ItemDePedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            return e.getMoedaPadrao().getSigla() + valor.toString();
        }
        return e.getMoedaPadrao().getSigla();
    }

    public BigDecimal getTotalItensBigDecimal() throws EDadoInvalidoException {
        if (listaDeItens != null) {
            BigDecimal valor = listaDeItens.getList().stream().map(ItemDePedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            return valor;
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalPedido() {
        try {
            BigDecimal a = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
            BigDecimal f = e.getFrete() == null ? BigDecimal.ZERO : e.getFrete();
            BigDecimal c = e.getDespesaCobranca() == null ? BigDecimal.ZERO : e.getDespesaCobranca();
            BigDecimal d = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();
            return getTotalItensBigDecimal().add(a.add(f.add(c))).subtract(d);
        } catch (DadoInvalidoException die) {
            die.print();
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalParcelasBigDecimal() throws EDadoInvalidoException {
        if (listaDeParcela != null) {
            BigDecimal valor = listaDeParcela.getList().stream().map(ParcelaDePedido::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            return valor;
        }
        return BigDecimal.ZERO;
    }

    public String getTotalParcelas() throws EDadoInvalidoException {
        if (listaDeParcela != null) {
            BigDecimal valor = listaDeParcela.getList().stream().map(ParcelaDePedido::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
            return e.getMoedaPadrao().getSigla() + valor.toString();
        }
        return e.getMoedaPadrao().getSigla();
    }

    public ModelList<ParcelaDePedido> getListaDeParcela() {
        return listaDeParcela;
    }

    public void setListaDeParcela(ModelList<ParcelaDePedido> listaDeParcela) {
        this.listaDeParcela = listaDeParcela;
    }

    public Model<ItemDePedido> getItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(Model<ItemDePedido> itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public ModelList<ItemDePedido> getListaDeItens() {
        return listaDeItens;
    }

    public void setListaDeItens(ModelList<ItemDePedido> listaDeItens) {
        this.listaDeItens = listaDeItens;
    }

    public Model<ItemDePedidoCancelado> getItemCanceladoSelecionado() {
        return itemCanceladoSelecionado;
    }

    public void setItemCanceladoSelecionado(Model<ItemDePedidoCancelado> itemCanceladoSelecionado) {
        this.itemCanceladoSelecionado = itemCanceladoSelecionado;
    }

    public ModelList<ItemDePedidoCancelado> getListaDeItensCancelados() {
        return listaDeItensCancelados;
    }

    public void setListaDeItensCancelados(ModelList<ItemDePedidoCancelado> listaDeItensCancelados) {
        this.listaDeItensCancelados = listaDeItensCancelados;
    }

}
