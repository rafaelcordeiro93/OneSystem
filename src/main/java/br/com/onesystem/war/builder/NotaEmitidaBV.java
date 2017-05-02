package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Parcela;
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
    private List<Parcela> parcelas;
    private List<Baixa> baixas;
    private ListaDePreco listaDePreco;
    private ValoresAVista valoresAVista;
    private Date emissao = new Date();
    private boolean cancelada = false;
    private FormaDeRecebimento formaDeRecebimento;
    private Credito credito;
    private Moeda moedaPadrao;
    private Orcamento orcamento;

    public NotaEmitidaBV(NotaEmitida notaEmitidaSelecionada) {
        this.id = notaEmitidaSelecionada.getId();
        this.pessoa = notaEmitidaSelecionada.getPessoa();
        this.operacao = notaEmitidaSelecionada.getOperacao();
        this.parcelas = notaEmitidaSelecionada.getParcelas();
        this.itensEmitidos = notaEmitidaSelecionada.getItensEmitidos();
        this.listaDePreco = notaEmitidaSelecionada.getListaDePreco();
        this.valoresAVista = notaEmitidaSelecionada.getValoresAVista();
        this.formaDeRecebimento = notaEmitidaSelecionada.getFormaDeRecebimento();
        this.emissao = notaEmitidaSelecionada.getEmissao();
        this.cancelada = notaEmitidaSelecionada.isCancelada();
        this.baixas = notaEmitidaSelecionada.getBaixaDinheiro();
        this.credito = notaEmitidaSelecionada.getCredito();
        this.moedaPadrao = notaEmitidaSelecionada.getMoedaPadrao();
        this.orcamento = notaEmitidaSelecionada.getOrcamento();
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

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comValoresAVista(valoresAVista)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).
                comPessoa(pessoa).comParcelas(parcelas).comEmissao(emissao).comFormaDeRecebimento(formaDeRecebimento)
                .cancelada(cancelada).comBaixas(baixas).comCredito(credito).comOrcamento(orcamento)
                .comMoedaPadrao(moedaPadrao).construir();
    }

    public NotaEmitida construirComID() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comId(id).comValoresAVista(valoresAVista)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao)
                .comPessoa(pessoa).comParcelas(parcelas).comFormaDeRecebimento(formaDeRecebimento)
                .comBaixas(baixas).comCredito(credito).comOrcamento(orcamento)
                .comEmissao(emissao).cancelada(cancelada).comBaixas(baixas).comMoedaPadrao(moedaPadrao)
                .construir();
    }
}
