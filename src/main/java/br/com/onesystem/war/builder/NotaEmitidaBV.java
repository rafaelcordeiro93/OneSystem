package br.com.onesystem.war.builder;

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
import java.util.List;

public class NotaEmitidaBV implements Serializable {

    private static final long serialVersionUID = 6686124108160060627L;

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemEmitido> itensEmitidos;
    private List<Titulo> titulos;
    private ListaDePreco listaDePreco;
    private BigDecimal acrescimo = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private FormaDeRecebimentoOuPagamento formaDeRecebimentoOuPagamento;
    private BigDecimal frete;
    private BigDecimal despesaCobranca;

    public NotaEmitidaBV(NotaEmitida notaEmitidaSelecionada) {
        this.id = notaEmitidaSelecionada.getId();
        this.pessoa = notaEmitidaSelecionada.getPessoa();
        this.operacao = notaEmitidaSelecionada.getOperacao();
        this.titulos = notaEmitidaSelecionada.getTitulos();
        this.itensEmitidos = notaEmitidaSelecionada.getItensEmitidos();
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
    
    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comAcrescimo(acrescimo).comDesconto(desconto).comFormaDeRecebimentoOuPagamento(formaDeRecebimentoOuPagamento)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).comFrete(frete)
                .comDespesaCobranca(despesaCobranca).comPessoa(pessoa).comTitulos(titulos).construir();
    }

    public NotaEmitida construirComID() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comId(id).comAcrescimo(acrescimo).comDesconto(desconto).comFormaDeRecebimentoOuPagamento(formaDeRecebimentoOuPagamento)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).comFrete(frete)
                .comDespesaCobranca(despesaCobranca).comPessoa(pessoa).comTitulos(titulos).construir();
    }
}
