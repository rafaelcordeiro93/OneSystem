/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.war.builder.ItemDePedidoBV;
import br.com.onesystem.war.builder.PedidoAFornecedoresBV;
import br.com.onesystem.war.builder.ParcelaDePedidoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class PedidoAFornecedoresView extends BasicMBImpl<PedidoAFornecedores, PedidoAFornecedoresBV> implements Serializable {

    private Model<ParcelaDePedido> parcelaSelecionada;
    private ModelList<ParcelaDePedido> listaDeParcela;
    private Model<ItemDePedido> itemSelecionado;
    private ModelList<ItemDePedido> listaDeItens;
    private ItemDePedidoBV itemDePedido;
    private ParcelaDePedidoBV parcelaDePedido;

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
            parcelaSelecionada = null;
            itemSelecionado = null;
            listaDeParcela = new ModelList<>();
            listaDeItens = new ModelList<>();
            itemDePedido = new ItemDePedidoBV();
            parcelaDePedido = new ParcelaDePedidoBV();
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
        } else if (obj instanceof Pessoa) {
            e.setPessoa((Pessoa) obj);
        } else if (obj instanceof Operacao) {
            e.setOperacao((Operacao) obj);
        } else if (obj instanceof Item) {
            itemDePedido.setItem((Item) obj);
        } else if (obj instanceof FormaDeRecebimento) {
            e.setFormaDeRecebimento((FormaDeRecebimento) obj);
        }
    }

    public void add() {
        try {
            PedidoAFornecedores p = e.construir();
            listaDeItens.getList().forEach((t) -> {
                p.adiciona(t);
            });
            listaDeParcela.getList().forEach((t) -> {
                p.adiciona(t);
            });
            new AdicionaDAO<>().adiciona(p);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
        InfoMessage.adicionado();
        RequestContext.getCurrentInstance().update("conteudo");
        limparJanela();
    }

    @Override
    public void update() {
        try {
            PedidoAFornecedores p = e.construirComID();
            List<ParcelaDePedido> parcelasRemovidas = listaDeParcela.getRemovidos().stream().filter(m -> ((ParcelaDePedido) m.getObject()).getId() != null).map(m -> (ParcelaDePedido) m.getObject()).collect(Collectors.toList());
            List<ItemDePedido> itemRemovidas = listaDeItens.getRemovidos().stream().filter(m -> ((ItemDePedido) m.getObject()).getId() != null).map(m -> (ItemDePedido) m.getObject()).collect(Collectors.toList());
            parcelasRemovidas.forEach(c -> p.remove(c));
            itemRemovidas.forEach(n -> p.remove(n));

            listaDeParcela.getList().forEach((t) -> {
                p.atualiza(t);
            });
            listaDeItens.getList().forEach((n) -> {
                p.atualiza(n);
            });

            new AtualizaDAO<>().atualiza(p);

            for (ItemDePedido v : itemRemovidas) {
                new RemoveDAO<>().remove(v, v.getId());
            }
            for (ParcelaDePedido c : parcelasRemovidas) {
                new RemoveDAO<>().remove(c, c.getId());
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
        itemDePedido = new ItemDePedidoBV((ItemDePedido) itemSelecionado.getObject());
    }

    public void adicionaItem() {
        try {
            listaDeItens.add(itemDePedido.construir());
            itemSelecionado = null;
            itemDePedido = new ItemDePedidoBV();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualizaItem() {
        try {
            if (itemSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_um_item"));
            }
            listaDeItens.set(itemSelecionado.getId().intValue(), itemDePedido.construir());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removerItem() {
        try {
            if (itemSelecionado == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_um_item"));
            }
            listaDeItens.remove(itemSelecionado);
            itemSelecionado = null;
            itemDePedido = new ItemDePedidoBV();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionaParcela(SelectEvent event) {
        parcelaSelecionada = (Model<ParcelaDePedido>) event.getObject();
        parcelaDePedido = new ParcelaDePedidoBV((ParcelaDePedido) parcelaSelecionada.getObject());
    }

    public void adicionaParcela() {
        try {
            listaDeParcela.add(parcelaDePedido.construir());
            parcelaSelecionada = null;
            parcelaDePedido = new ParcelaDePedidoBV();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualizaParcela() {
        try {
            if (parcelaSelecionada == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_uma_parcela"));
            }
            listaDeParcela.set(parcelaSelecionada.getId().intValue(), parcelaDePedido.construir());
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removerParcela() {
        try {
            if (parcelaSelecionada == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("selecione_uma_parcela"));
            }
            listaDeParcela.remove(parcelaSelecionada);
            parcelaSelecionada = null;
            parcelaDePedido = new ParcelaDePedidoBV();
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

    public void atualizaValorDinheiro() {
        try {
            e.setTotalEmDinheiro(getTotalPedido().subtract(getTotalParcelasBigDecimal()));
        } catch (DadoInvalidoException die) {
            die.print();
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

//    /**
//     * Calcula a porcentagem de acréscimo e desconto após informar um dos campos
//     * de valor de acréscimo e desconto.
//     */
//    public void calculaPorcentagemAcrescimoEDesconto() {
//        BigDecimal total = e.getTotalItens();
//        if (total.compareTo(BigDecimal.ZERO) > 0) {
//            BigDecimal acrescimo = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
//            BigDecimal desconto = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();
//            BigDecimal pAcrescimo;
//            BigDecimal pDesconto;
//            BigDecimal cem = new BigDecimal(100);
//
//            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
//                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
//                e.setPorcentagemAcrescimo(pAcrescimo);
//            } else {
//                e.setPorcentagemAcrescimo(BigDecimal.ZERO);
//            }
//            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
//                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
//                e.setPorcentagemDesconto(pDesconto);
//            } else {
//                e.setPorcentagemDesconto(BigDecimal.ZERO);
//            }
//        }
//    }
//
//    public String getValorRestante() {
//
//        BigDecimal total = e.getTotalEmDinheiro();
//        BigDecimal valorAReceber = BigDecimal.ZERO;
//        NumberFormat nf = NumberFormat.getCurrencyInstance(t.getMoedaPadrao().getBandeira().getLocal());
//
//        if (total == null || total.subtract(valorAReceber).compareTo(BigDecimal.ZERO) < 0) {
//            return nf.format(BigDecimal.ZERO);
//        } else {
//            return nf.format(total.subtract(valorAReceber));
//        }
//    }
//
//    public String getTotalConvertidoRecebidoFormatado() {
//        return NumberFormat.getCurrencyInstance(t.getMoedaPadrao().getBandeira().getLocal()).format(getTotalConvertidoRecebido());
//    }
//
//    public BigDecimal getTotalParcelas() {
//        BigDecimal totalParcela = BigDecimal.ZERO;
//        for (ParcelaDePedidoBV p : parcelaDePedidos) {
//            if (p.getCotacao() != null && p.getCotacao() != cotacao) {
//                totalParcela = totalParcela.add(p.getValor().divide(p.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
//            } else {
//                totalParcela = totalParcela.add(p.getValor());
//            }
//        }
//
//        return totalParcela;
//    }
//
//    public String getTotalParcelasFormatado() {
//        BigDecimal totalParcelas = getTotalParcelas();
//
//        return totalParcelas.compareTo(BigDecimal.ZERO) == 0 ? ""
//                : NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(totalParcelas);
//    }
    //----------------------- Getters and Setters -----------------------------
    //------------------- Fim Getters and Setters -----------------------------
    public Model<ParcelaDePedido> getParcelaSelecionada() {
        return parcelaSelecionada;
    }

    public void setParcelaSelecionada(Model<ParcelaDePedido> parcelaSelecionada) {
        this.parcelaSelecionada = parcelaSelecionada;
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

    public ItemDePedidoBV getItemDePedido() {
        return itemDePedido;
    }

    public void setItemDePedido(ItemDePedidoBV itemDePedido) {
        this.itemDePedido = itemDePedido;
    }

    public ParcelaDePedidoBV getParcelaDePedido() {
        return parcelaDePedido;
    }

    public void setParcelaDePedido(ParcelaDePedidoBV parcelaDePedido) {
        this.parcelaDePedido = parcelaDePedido;
    }

}
