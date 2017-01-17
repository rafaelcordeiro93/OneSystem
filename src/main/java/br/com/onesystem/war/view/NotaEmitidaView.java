/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.domain.builder.ParcelaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.CotacaoValores;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.EstoqueBV;
import br.com.onesystem.war.builder.FormaDeRecebimentoOuPagamentoBV;
import br.com.onesystem.war.builder.ItemEmitidoBV;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.builder.ParcelaBV;
import br.com.onesystem.war.builder.QuantidadeDeItemBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@ManagedBean
@ViewScoped
public class NotaEmitidaView implements Serializable {

    private FormaDeRecebimentoOuPagamentoBV formaDeRecebimentoOuPagamento;
    private NotaEmitida notaEmitidaSelecionada;
    private NotaEmitidaBV notaEmitida;
    private ItemEmitidoBV itemEmitido;
    private ItemEmitido itemEmitidoSelecionado;
    private EstoqueBV estoqueBV;
    private Configuracao configuracao;
    private CotacaoValores cotacaoValoresSelecionado;
    private List<Cotacao> cotacaoLista;
    private List<CotacaoValores> cotacoes;
    private List<ParcelaBV> parcelas;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService configuracaoService;

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

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

    public void finalizar() {
        try {
            notaEmitida.setFormaDeRecebimentoOuPagamento(formaDeRecebimentoOuPagamento.construir());
            NotaEmitida novoRegistro = notaEmitida.construir();
            preparaInclusaoDeItemEmitido(novoRegistro);
            preparaInclusaoDeRecebimentoDeValores(novoRegistro);
            new AdicionaDAO<NotaEmitida>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        } catch (ConstraintViolationException cpe) {
            ErrorMessage.print(cpe.toString());
        }
    }

    private void preparaInclusaoDeItemEmitido(NotaEmitida novoRegistro) throws ConstraintViolationException, DadoInvalidoException {
        for (ItemEmitido ie : novoRegistro.getItensEmitidos()) {
            ie.preparaInclusao(novoRegistro);
            preparaInclusaoDeEstoque(ie);
        }
    }

    private void preparaInclusaoDeEstoque(ItemEmitido ie) {
        for (Estoque estoque : ie.getEstoque()) {
            estoque.preparaInclusaoDe(ie);
        }
    }

    public void selecionaItemEmitido(SelectEvent event) {
        this.itemEmitidoSelecionado = (ItemEmitido) event.getObject();
        this.itemEmitido = new ItemEmitidoBV(itemEmitidoSelecionado);
    }

    public void selecionaFormaDeRecebimento(SelectEvent e) {
        FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) e.getObject();
        formaDeRecebimentoOuPagamento.setFormaDeRecebimento(formaDeRecebimento);
        calculaTotaisFormaDeRecebimento(formaDeRecebimento);
    }

    private void calculaTotaisFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        if (formaDeRecebimento.getPorcentagemDeEntrada().compareTo(BigDecimal.ZERO) > 0
                && getTotalItens().compareTo(BigDecimal.ZERO) > 0) {
            calculaEntradaPadrao(formaDeRecebimento);
        }
    }

    private void calculaEntradaPadrao(FormaDeRecebimento formaDeRecebimento) {
        BigDecimal cem = new BigDecimal(100);
        BigDecimal p = formaDeRecebimento.getPorcentagemDeEntrada().divide(cem, 2, BigDecimal.ROUND_UP);
        BigDecimal resultado = p.multiply(getTotalNota());
        alteraValorDeFormaDeRecebimento(formaDeRecebimento, resultado);
    }

    private void alteraValorDeFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento, BigDecimal resultado) {
        switch (formaDeRecebimento.getFormaPadraoDeEntrada()) {
            case DINHEIRO:
                formaDeRecebimentoOuPagamento.setDinheiro(resultado);
                break;
            case CREDITO:
                formaDeRecebimentoOuPagamento.setCredito(resultado);
                break;
            case CHEQUE:
                formaDeRecebimentoOuPagamento.setCheque(resultado);
                break;
            case A_FATURAR:
                formaDeRecebimentoOuPagamento.setaFaturar(resultado);
                break;
            case CARTAO:
                formaDeRecebimentoOuPagamento.setCartao(resultado);
                break;
            default:
                break;
        }
    }

    private BigDecimal getTotalNota() {
        BigDecimal acrescimo = notaEmitida.getAcrescimo() == null ? BigDecimal.ZERO : notaEmitida.getAcrescimo();
        BigDecimal frete = notaEmitida.getFrete() == null ? BigDecimal.ZERO : notaEmitida.getFrete();
        BigDecimal despesaCobranca = notaEmitida.getDespesaCobranca() == null ? BigDecimal.ZERO : notaEmitida.getDespesaCobranca();
        BigDecimal desconto = notaEmitida.getDesconto() == null ? BigDecimal.ZERO : notaEmitida.getDesconto();

        return getTotalItens().add(acrescimo.add(frete.add(despesaCobranca)).subtract(desconto));
    }

    /**
     * Método que criará as parcelas automaticamente ao informar e sair do campo
     * número de parcelas.
     */
    public void criaParcelas() {
        FormaDeRecebimentoOuPagamentoBV f = formaDeRecebimentoOuPagamento;
        Integer numParcelas = f.getParcelas(); //Número de parcelas
        TipoPeriodicidade tipoPeridiocidade = f.getFormaDeRecebimento().getTipoPeriodicidade();
        Integer periodicidade = f.getFormaDeRecebimento().getPeriodicidade();

        if (numParcelas != null && numParcelas > 0) {

            BigDecimal valorParcelado = getTotalNota().divide(new BigDecimal(numParcelas), 2, BigDecimal.ROUND_UP);

            // Busca o primeiro vencimento das parcelas
            Date vencimento = new DateUtil().getPeriodicidadeCalculada(new Date(), tipoPeridiocidade, periodicidade);

            parcelas = new ArrayList<>();
            for (int i = 1; i <= numParcelas; i++) {
                parcelas.add(new ParcelaBuilder().comID(getIdParcela()).comValor(valorParcelado)
                        .comVencimento(vencimento).comTipoFormaDeRecebimentoParcela(f.getFormaDeRecebimento().getFormaPadraoDeParcela()).construir());
                vencimento = new DateUtil().getPeriodicidadeCalculada(vencimento, tipoPeridiocidade, periodicidade);
            }
        }
    }

    private Long getIdParcela() {
        Long id = (long) 1;
        if (!parcelas.isEmpty()) {
            for (ParcelaBV p : parcelas) {
                if (p.getId() >= id) {
                    id = p.getId() + 1;
                }
            }
        }
        return id;
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

    public void preparaInclusaoDeRecebimentoDeValores(NotaEmitida novoRegistro) throws DadoInvalidoException {
        if (novoRegistro.getFormaDeRecebimentoOuPagamento().getDinheiro().compareTo(BigDecimal.ZERO) > 0) {
            Baixa baixa = new BaixaBuilder().cancelada(false)
                    .comConta(null).comDesconto(novoRegistro.getDesconto()).comEmissao(novoRegistro.getEmissao())
                    .comNaturezaFinanceira(novoRegistro.getOperacao().getOperacaoFinanceira())
                    .comPessoa(novoRegistro.getPessoa()).comReceita(novoRegistro.getOperacao().getVendaAVista()).
                    comTotal(getTotalNota()).comValor(getTotalNota()).comNotaEmitida(novoRegistro).construir();
            novoRegistro.getBaixas().add(baixa);
        }
    }

    public void addItemNaLista() {
        try {
            itemEmitido.setId(retornarCodigoItem());
            notaEmitida.getItensEmitidos().add(itemEmitido.construirComId());
            limparItemEmitido();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private Long retornarCodigoItem() {
        Long id = (long) 1;
        if (!notaEmitida.getItensEmitidos().isEmpty()) {
            for (ItemEmitido dp : notaEmitida.getItensEmitidos()) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public void updateItemNaLista() {
        try {
            if (itemEmitidoSelecionado != null) {
                notaEmitida.getItensEmitidos().set(notaEmitida.getItensEmitidos().indexOf(itemEmitidoSelecionado),
                        itemEmitido.construirComId());
                limparItemEmitido();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemEmitidoSelecionado != null) {
            notaEmitida.getItensEmitidos().remove(itemEmitidoSelecionado);
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
        try {
            List<QuantidadeDeItemBV> lista = (List<QuantidadeDeItemBV>) event.getObject();
            itemEmitido.setEstoque(criarBaixaDeEstoque(lista));
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public String getTotalItensFormatado() {
        if (notaEmitida.getItensEmitidos().isEmpty()) {
            return "";
        } else {
            return configuracao.getMoedaPadrao().getSigla() + " " + NumberFormat.getNumberInstance().format(getTotalItens());
        }
    }

    private BigDecimal getTotalItens() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemEmitido i : notaEmitida.getItensEmitidos()) {
            total = total.add(i.getTotal());
        }
        return total;
    }

    private List<Estoque> criarBaixaDeEstoque(List<QuantidadeDeItemBV> lista) throws DadoInvalidoException {
        List<Estoque> estoquesBV = new ArrayList<Estoque>();
        for (QuantidadeDeItemBV q : lista) {
            estoqueBV = new EstoqueBV();
            estoqueBV.setTipo(notaEmitida.getOperacao().getContasDeEstoque().get(0).getOperacaoFisica());
            estoqueBV.setDeposito(q.getSaldoDeEstoque().getDeposito());
            estoqueBV.setQuantidade(q.getQuantidade());
            estoqueBV.setItem(itemEmitido.getItem());
            estoquesBV.add(estoqueBV.construir());
        }
        return estoquesBV;
    }

    public void limparJanela() {
        notaEmitida = new NotaEmitidaBV();
        itemEmitido = new ItemEmitidoBV();
        notaEmitida.setItensEmitidos(new ArrayList<ItemEmitido>());
        formaDeRecebimentoOuPagamento = new FormaDeRecebimentoOuPagamentoBV();
        notaEmitidaSelecionada = null;
        inicializaCotacoes();
    }

    public String getValorRestante() {
        BigDecimal total = formaDeRecebimentoOuPagamento.getDinheiro();
        BigDecimal valorAReceber = BigDecimal.ZERO;
        for (CotacaoValores c : cotacoes) {
            valorAReceber = valorAReceber.add(c.getValorConvertidoRecebido());
        }
        return total == null ? NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(BigDecimal.ZERO)
                : total.subtract(valorAReceber).compareTo(BigDecimal.ZERO) < 0 ? NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(BigDecimal.ZERO)
                : NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(total.subtract(valorAReceber));
    }

    public String getGanhoDeCambio() {
        BigDecimal total = formaDeRecebimentoOuPagamento.getDinheiro();
        BigDecimal valorAReceber = BigDecimal.ZERO;
        for (CotacaoValores c : cotacoes) {
            valorAReceber = valorAReceber.add(c.getValorConvertidoRecebido());
        }
        return total == null ? NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(BigDecimal.ZERO)
                : total.subtract(valorAReceber).compareTo(BigDecimal.ZERO) < 0 ? NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(total.subtract(valorAReceber).multiply(new BigDecimal(-1)))
                : NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(BigDecimal.ZERO);
    }

    public void addTotalCotacoes() {
        formaDeRecebimentoOuPagamento.setMoeda(configuracao.getMoedaPadrao());
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("cotacaoVal");
        for (CotacaoValores c : cotacoes) {
            c.setTotal(formaDeRecebimentoOuPagamento.getDinheiro());
            c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
        }
        rc.execute("PF('cotacaoVal').show()");
    }

    public BigDecimal getTotalConvertidoRecebido() {
        BigDecimal total = BigDecimal.ZERO;
        for (CotacaoValores c : cotacoes) {
            total = total.add(c.getValorConvertidoRecebido());
        }
        return total;
    }

    private void inicializaCotacoes() {
        cotacaoLista = service.buscarCotacoesDoDiaAtual();
        cotacoes = new ArrayList<CotacaoValores>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new CotacaoValores(c, null, null, null, configuracao.getMoedaPadrao()));
        }
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

    public FormaDeRecebimentoOuPagamentoBV getFormaDeRecebimentoOuPagamento() {
        return formaDeRecebimentoOuPagamento;
    }

    public void setFormaDeRecebimentoOuPagamento(FormaDeRecebimentoOuPagamentoBV formaDeRecebimentoOuPagamento) {
        this.formaDeRecebimentoOuPagamento = formaDeRecebimentoOuPagamento;
    }

    public EstoqueBV getEstoqueBV() {
        return estoqueBV;
    }

    public void setEstoqueBV(EstoqueBV estoqueBV) {
        this.estoqueBV = estoqueBV;
    }

    public CotacaoValores getCotacaoValoresSelecionado() {
        return cotacaoValoresSelecionado;
    }

    public void setCotacaoValoresSelecionado(CotacaoValores cotacaoValoresSelecionado) {
        this.cotacaoValoresSelecionado = cotacaoValoresSelecionado;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public List<CotacaoValores> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<CotacaoValores> cotacoes) {
        this.cotacoes = cotacoes;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }

    public List<ParcelaBV> getParcelas() {
        return parcelas;
    }

}
