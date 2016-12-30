package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.FormaDeRecebimentoOuPagamento;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.NotaEmitidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class NotaEmitidaBV implements Serializable {

    private static final long serialVersionUID = 6686124108160060627L;

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemEmitido> itensEmitidos;
    private List<Titulo> titulos;
    private List<Baixa> baixas;
    private ListaDePreco listaDePreco;
    private BigDecimal acrescimo = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private FormaDeRecebimentoOuPagamento formaDeRecebimentoOuPagamento;
    private BigDecimal frete;
    private BigDecimal despesaCobranca;
    private Date emissao;
    private boolean cancelada = false;

    public NotaEmitidaBV(NotaEmitida notaEmitidaSelecionada) {
        this.id = notaEmitidaSelecionada.getId();
        this.pessoa = notaEmitidaSelecionada.getPessoa();
        this.operacao = notaEmitidaSelecionada.getOperacao();
        this.titulos = notaEmitidaSelecionada.getTitulos();
        this.itensEmitidos = notaEmitidaSelecionada.getItensEmitidos();
        this.listaDePreco = notaEmitidaSelecionada.getListaDePreco();
        this.acrescimo = notaEmitidaSelecionada.getAcrescimo();
        this.desconto = notaEmitidaSelecionada.getDesconto();
        this.formaDeRecebimentoOuPagamento = notaEmitidaSelecionada.getFormaDeRecebimentoOuPagamento();
        this.frete = notaEmitidaSelecionada.getFrete();
        this.despesaCobranca = notaEmitidaSelecionada.getDespesasCobranca();
        this.emissao = notaEmitidaSelecionada.getEmissao();
        this.cancelada = notaEmitidaSelecionada.isCancelada();
        this.baixas = notaEmitidaSelecionada.getBaixas();
    }

    public NotaEmitidaBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public void setListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }

    public void setItensEmitidos(List<ItemEmitido> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public FormaDeRecebimentoOuPagamento getFormaDeRecebimentoOuPagamento() {
        return formaDeRecebimentoOuPagamento;
    }

    public void setFormaDeRecebimentoOuPagamento(FormaDeRecebimentoOuPagamento formaDeRecebimentoOuPagamento) {
        this.formaDeRecebimentoOuPagamento = formaDeRecebimentoOuPagamento;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public void setDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }
    
    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comAcrescimo(acrescimo).comDesconto(desconto).comFormaDeRecebimentoOuPagamento(formaDeRecebimentoOuPagamento)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).comFrete(frete)
                .comDespesaCobranca(despesaCobranca).comPessoa(pessoa).comTitulos(titulos).comEmissao(emissao)
                .cancelada(cancelada).comBaixas(baixas).construir();
    }

    public NotaEmitida construirComID() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comId(id).comAcrescimo(acrescimo).comDesconto(desconto).comFormaDeRecebimentoOuPagamento(formaDeRecebimentoOuPagamento)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).comFrete(frete)
                .comDespesaCobranca(despesaCobranca).comPessoa(pessoa).comTitulos(titulos)
                .comEmissao(emissao).cancelada(cancelada).comBaixas(baixas).construir();
    }
}
