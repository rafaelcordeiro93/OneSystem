/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaEmitidaBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemEmitido> itensEmitidos;
    private List<Titulo> titulos;
    private ListaDePreco listaDePreco;
    private ValoresAVista valoresAVista;
    private Date emissao;
    private boolean cancelada = false;
    private List<Baixa> baixas;
    private FormaDeRecebimento formaDeRecebimento;
    private Credito credito;
    private List<Cheque> cheques;
    private List<BoletoDeCartao> boletoDeCartao;

    public NotaEmitidaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public NotaEmitidaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public NotaEmitidaBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public NotaEmitidaBuilder comItensEmitidos(List<ItemEmitido> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
        return this;
    }

    public NotaEmitidaBuilder comTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
        return this;
    }

    public NotaEmitidaBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public NotaEmitidaBuilder comValoresAVista(ValoresAVista valoresAVista) {
        this.valoresAVista = valoresAVista;
        return this;
    }

    public NotaEmitidaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public NotaEmitidaBuilder cancelada(boolean cancelada) {
        this.cancelada = cancelada;
        return this;
    }

    public NotaEmitidaBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public NotaEmitidaBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public NotaEmitidaBuilder comCredito(Credito credito) {
        this.credito = credito;
        return this;
    }

    public NotaEmitidaBuilder comCheque(List<Cheque> cheques) {
        this.cheques = cheques;
        return this;
    }

    public NotaEmitidaBuilder comBoletoDeCartao(List<BoletoDeCartao> boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
        return this;
    }

    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitida(id, pessoa, operacao, itensEmitidos, formaDeRecebimento, listaDePreco, valoresAVista, baixas, emissao, cancelada, credito, cheques, boletoDeCartao, titulos);
    }

}
