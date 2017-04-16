package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.NotaEmitidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NotaEmitidaBV implements Serializable, BuilderView<NotaEmitida> {
    
    private static final long serialVersionUID = 6686124108160060627L;
    
    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemEmitido> itensEmitidos;
    private List<Titulo> titulos;
    private List<Baixa> baixas;
    private ListaDePreco listaDePreco;
    private ValoresAVista valoresAVista;
    private Date emissao = new Date();
    private boolean cancelada = false;
    private FormaDeRecebimento formaDeRecebimento;
    private List<Cheque> cheques;
    private List<BoletoDeCartao> cartoes;
    private Credito credito;
    private Moeda moedaPadrao;
    
    public NotaEmitidaBV(NotaEmitida notaEmitidaSelecionada) {
        this.id = notaEmitidaSelecionada.getId();
        this.pessoa = notaEmitidaSelecionada.getPessoa();
        this.operacao = notaEmitidaSelecionada.getOperacao();
        this.titulos = notaEmitidaSelecionada.getTitulos();
        this.itensEmitidos = notaEmitidaSelecionada.getItensEmitidos();
        this.listaDePreco = notaEmitidaSelecionada.getListaDePreco();
        this.valoresAVista = notaEmitidaSelecionada.getValoresAVista();
        this.formaDeRecebimento = notaEmitidaSelecionada.getFormaDeRecebimento();
        this.emissao = notaEmitidaSelecionada.getEmissao();
        this.cancelada = notaEmitidaSelecionada.isCancelada();
        this.baixas = notaEmitidaSelecionada.getBaixaDinheiro();
        this.cheques = notaEmitidaSelecionada.getCheques();
        this.cartoes = notaEmitidaSelecionada.getCartoes();
        this.credito = notaEmitidaSelecionada.getCredito();
        this.moedaPadrao = notaEmitidaSelecionada.getMoedaPadrao();
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
    
    public Credito getCredito() {
        return credito;
    }
    
    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }
    
    public void setMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
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
    
    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }
    
    public void setFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }
    
    public ValoresAVista getValoresAVista() {
        return valoresAVista;
    }
    
    public void setValoresAVista(ValoresAVista valoresAVista) {
        this.valoresAVista = valoresAVista;
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
    
    public List<Cheque> getCheques() {
        return cheques;
    }
    
    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }
    
    public List<BoletoDeCartao> getCartoes() {
        return cartoes;
    }
    
    public void setCartoes(List<BoletoDeCartao> cartoes) {
        this.cartoes = cartoes;
    }
    
    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comValoresAVista(valoresAVista)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).
                comPessoa(pessoa).comTitulos(titulos).comEmissao(emissao).comFormaDeRecebimento(formaDeRecebimento)
                .cancelada(cancelada).comBaixas(baixas).comCheque(cheques).comBoletoDeCartao(cartoes).comCredito(credito)
                .comMoedaPadrao(moedaPadrao).construir();
    }
    
    public NotaEmitida construirComID() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comId(id).comValoresAVista(valoresAVista)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao)
                .comPessoa(pessoa).comTitulos(titulos).comFormaDeRecebimento(formaDeRecebimento)
                .comBaixas(baixas).comCheque(cheques).comBoletoDeCartao(cartoes).comCredito(credito)
                .comEmissao(emissao).cancelada(cancelada).comBaixas(baixas).comMoedaPadrao(moedaPadrao)
                .construir();
    }
}
