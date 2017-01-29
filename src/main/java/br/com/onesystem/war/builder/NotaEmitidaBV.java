package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ValoresAVista;
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
    private ValoresAVista valoresAVista;
    private Date emissao;
    private boolean cancelada = false;
    private FormaDeRecebimento formaDeRecebimento;

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

    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comValoresAVista(valoresAVista)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao).
                comPessoa(pessoa).comTitulos(titulos).comEmissao(emissao)
                .cancelada(cancelada).comBaixas(baixas).construir();
    }

    public NotaEmitida construirComID() throws DadoInvalidoException {
        return new NotaEmitidaBuilder().comId(id).comValoresAVista(valoresAVista)
                .comItensEmitidos(itensEmitidos).comListaDePreco(listaDePreco).comOperacao(operacao)
                .comPessoa(pessoa).comTitulos(titulos)
                .comEmissao(emissao).cancelada(cancelada).comBaixas(baixas).construir();
    }
}
