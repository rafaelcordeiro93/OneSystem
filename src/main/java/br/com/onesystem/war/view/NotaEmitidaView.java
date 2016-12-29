/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ItemPorDeposito;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.builder.ItemEmitidoBV;
import br.com.onesystem.war.builder.ItemPorDepositoBV;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.builder.QuantidadeDeItemBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.view.dialogo.QuantidadeDeItemView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@ManagedBean
@ViewScoped
public class NotaEmitidaView implements Serializable {
    
    private NotaEmitida notaEmitidaSelecionada;
    private NotaEmitidaBV notaEmitida;
    private ItemEmitidoBV itemEmitido;
    private ItemEmitido itemEmitidoSelecionado;
    private List<ItemEmitido> itensEmitidos;
    private ItemPorDepositoBV itemPorDepositoBV;
    private List<ItemPorDeposito> itensPorDeposito;
    private Configuracao configuracao;
    
    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService configuracaoService;
    
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
        limpaSessao();
    }
    
    private void limpaSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        if (session.getAttribute("onesystem.item.token") != null) {
            session.removeAttribute("onesystem.item.token");
        }
    }
    
    private void iniciarConfiguracoes() {
        try {
            configuracao = configuracaoService.buscar();
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }
    
    public void add() {
        try {
            NotaEmitida novoRegistro = notaEmitida.construir();
            new AdicionaDAO<NotaEmitida>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    public void update() {
        try {
            if (notaEmitidaSelecionada != null) {
                NotaEmitida notaEmitidaExistente = notaEmitida.construirComID();
                new AtualizaDAO<NotaEmitida>(NotaEmitida.class).atualiza(notaEmitidaExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La notaEmitida no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    public void delete() {
        try {
            if (notaEmitidaSelecionada != null) {
                new RemoveDAO<NotaEmitida>(NotaEmitida.class).remove(notaEmitidaSelecionada, notaEmitidaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }
    
    public void selecionaItemEmitido(SelectEvent event) {
        this.itemEmitidoSelecionado = (ItemEmitido) event.getObject();
        this.itemEmitido = new ItemEmitidoBV(itemEmitidoSelecionado);
    }
    
    public void selecionaFormaDeRecebimento(SelectEvent e) {
        FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) e.getObject();
        notaEmitida.setFormaDeRecebimento(formaDeRecebimento);
    }    

    public void selecionaOperacao(SelectEvent event) {
        notaEmitida.setOperacao((Operacao) event.getObject());
    }
    
    public void selecionaPessoa(SelectEvent event) {
        notaEmitida.setPessoa((Pessoa) event.getObject());
    }
    
    public void selecionaListaDePreco(SelectEvent event) {
        notaEmitida.setListaDePreco((ListaDePreco) event.getObject());
    }
    
    public void selecionaNotaEmitida(SelectEvent e) {
        NotaEmitida r = (NotaEmitida) e.getObject();
        notaEmitida = new NotaEmitidaBV(r);
        notaEmitidaSelecionada = r;
    }
    
    public void addItemNaLista() {
        try {
            itemEmitido.setId(new Date().getTime());
            itensEmitidos.add(itemEmitido.construirComId());
            limparItemEmitido();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }
    
    public void updateItemNaLista() {
        try {
            if (itemEmitidoSelecionado != null) {
                itensEmitidos.set(itensEmitidos.indexOf(itemEmitidoSelecionado),
                        itemEmitido.construirComId());
                limparItemEmitido();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }
    
    public void deleteItemNaLista() {
        if (itemEmitidoSelecionado != null) {
            itensEmitidos.remove(itemEmitidoSelecionado);
            limparItemEmitido();
        }
    }
    
    public void limparItemEmitido() {
        itemEmitido = new ItemEmitidoBV();
        itemEmitidoSelecionado = null;
    }
    
    public void selecionaItem(SelectEvent event) {
        itemEmitido.setItem((Item) event.getObject());
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("onesystem.item.token", itemEmitido.getItem());
    }
    
    public void selecionaQuantidadeDeItemBV(SelectEvent event) {
        List<QuantidadeDeItemBV> lista = (List<QuantidadeDeItemBV>) event.getObject();
        criaItemPorDeposito(lista);
        itemEmitido.setListaDeItemPorDeposito(itensPorDeposito);
    }
    
    private void criaItemPorDeposito(List<QuantidadeDeItemBV> lista) {
        itensPorDeposito = new ArrayList<ItemPorDeposito>();
        try {
            for (QuantidadeDeItemBV q : lista) {
                itemPorDepositoBV = new ItemPorDepositoBV();
                itemPorDepositoBV.setDeposito(q.getSaldoDeEstoque().getDeposito());
                itemPorDepositoBV.setQuantidade(q.getQuantidade());
                itensPorDeposito.add(itemPorDepositoBV.construir());
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    public void limparJanela() {
        notaEmitida = new NotaEmitidaBV();
        itemEmitido = new ItemEmitidoBV();
        itemPorDepositoBV = new ItemPorDepositoBV();
        itensEmitidos = new ArrayList<ItemEmitido>();
        itensPorDeposito = new ArrayList<ItemPorDeposito>();
        notaEmitidaSelecionada = null;
    }
    
    public void desfazer() {
        if (notaEmitidaSelecionada != null) {
            notaEmitida = new NotaEmitidaBV(notaEmitidaSelecionada);
        }
    }
    
    public NotaEmitida getNotaEmitidaSelecionada() {
        return notaEmitidaSelecionada;
    }
    
    public void setNotaEmitidaSelecionada(NotaEmitida notaEmitidaSelecionada) {
        this.notaEmitidaSelecionada = notaEmitidaSelecionada;
    }
    
    public NotaEmitidaBV getNotaEmitida() {
        return notaEmitida;
    }
    
    public void setNotaEmitida(NotaEmitidaBV notaEmitida) {
        this.notaEmitida = notaEmitida;
    }
    
    public ItemEmitidoBV getItemEmitido() {
        return itemEmitido;
    }
    
    public void setItemEmitido(ItemEmitidoBV itemEmitido) {
        this.itemEmitido = itemEmitido;
    }
    
    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }
    
    public void setItensEmitidos(List<ItemEmitido> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
    }
    
    public ItemPorDepositoBV getItemPorDepositoBV() {
        return itemPorDepositoBV;
    }
    
    public void setItemPorDepositoBV(ItemPorDepositoBV itemPorDepositoBV) {
        this.itemPorDepositoBV = itemPorDepositoBV;
    }
    
    public List<ItemPorDeposito> getItensPorDeposito() {
        return itensPorDeposito;
    }
    
    public void setItensPorDeposito(List<ItemPorDeposito> itensPorDeposito) {
        this.itensPorDeposito = itensPorDeposito;
    }
    
    public Configuracao getConfiguracao() {
        return configuracao;
    }
    
    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }
    
    public ConfiguracaoService getConfiguracaoService() {
        return configuracaoService;
    }
    
    public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }
    
    public ItemEmitido getItemEmitidoSelecionado() {
        return itemEmitidoSelecionado;
    }
    
    public void setItemEmitidoSelecionado(ItemEmitido itemEmitidoSelecionado) {
        this.itemEmitidoSelecionado = itemEmitidoSelecionado;
    }
    
}
