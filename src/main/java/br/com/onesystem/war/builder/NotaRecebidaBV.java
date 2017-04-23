package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.ItemRecebido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Parcela;
import br.com.onesystem.domain.builder.NotaRecebidaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NotaRecebidaBV implements Serializable, BuilderView<NotaRecebida> {

    private static final long serialVersionUID = 6686124108160060627L;

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemRecebido> itensRecebidos;
    private List<Parcela> parcelas;
    private List<Baixa> baixas;
    private ListaDePreco listaDePreco;
    private ValoresAVista valoresAVista;
    private Date emissao = new Date();
    private boolean cancelada = false;
    private FormaDeRecebimento formaDeRecebimento;
    private Credito credito;
    private Moeda moedaPadrao;

    public NotaRecebidaBV(NotaRecebida notaRecebidaSelecionada) {
        this.id = notaRecebidaSelecionada.getId();
        this.pessoa = notaRecebidaSelecionada.getPessoa();
        this.operacao = notaRecebidaSelecionada.getOperacao();
        this.parcelas = notaRecebidaSelecionada.getParcelas();
        this.itensRecebidos = notaRecebidaSelecionada.getItensRecebidos();
        this.listaDePreco = notaRecebidaSelecionada.getListaDePreco();
        this.valoresAVista = notaRecebidaSelecionada.getValoresAVista();
        this.formaDeRecebimento = notaRecebidaSelecionada.getFormaDeRecebimento();
        this.emissao = notaRecebidaSelecionada.getEmissao();
        this.cancelada = notaRecebidaSelecionada.isCancelada();
        this.baixas = notaRecebidaSelecionada.getBaixaDinheiro();
        this.credito = notaRecebidaSelecionada.getCredito();
        this.moedaPadrao = notaRecebidaSelecionada.getMoedaPadrao();
    }

    public NotaRecebidaBV() {
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

    public List<ItemRecebido> getItensRecebidos() {
        return itensRecebidos;
    }

    public void setItensRecebidos(List<ItemRecebido> itensRecebidos) {
        this.itensRecebidos = itensRecebidos;
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

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public NotaRecebida construir() throws DadoInvalidoException {
        return new NotaRecebidaBuilder().comValoresAVista(valoresAVista)
                .comItensRecebidos(itensRecebidos).comListaDePreco(listaDePreco).comOperacao(operacao).
                comPessoa(pessoa).comParcelas(parcelas).comEmissao(emissao).comFormaDeRecebimento(formaDeRecebimento)
                .cancelada(cancelada).comBaixas(baixas).comCredito(credito)
                .comMoedaPadrao(moedaPadrao).construir();
    }

    public NotaRecebida construirComID() throws DadoInvalidoException {
        return new NotaRecebidaBuilder().comId(id).comValoresAVista(valoresAVista)
                .comItensRecebidos(itensRecebidos).comListaDePreco(listaDePreco).comOperacao(operacao)
                .comPessoa(pessoa).comParcelas(parcelas).comFormaDeRecebimento(formaDeRecebimento)
                .comBaixas(baixas).comCredito(credito)
                .comEmissao(emissao).cancelada(cancelada).comBaixas(baixas).comMoedaPadrao(moedaPadrao)
                .construir();
    }
}
