/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.ItemRecebido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Parcela;
import br.com.onesystem.exception.DadoInvalidoException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaRecebidaBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemRecebido> itensRecebidos;
    private ListaDePreco listaDePreco;
    private ValoresAVista valoresAVista;
    private Date emissao;
    private boolean cancelada = false;
    private List<Baixa> baixas;
    private FormaDeRecebimento formaDeRecebimento;
    private Credito credito;
    private List<Parcela> parcelas;
    private Moeda moedaPadrao;

    public NotaRecebidaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public NotaRecebidaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public NotaRecebidaBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public NotaRecebidaBuilder comItensRecebidos(List<ItemRecebido> itensRecebidos) {
        this.itensRecebidos = itensRecebidos;
        return this;
    }

    public NotaRecebidaBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public NotaRecebidaBuilder comValoresAVista(ValoresAVista valoresAVista) {
        this.valoresAVista = valoresAVista;
        return this;
    }

    public NotaRecebidaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public NotaRecebidaBuilder cancelada(boolean cancelada) {
        this.cancelada = cancelada;
        return this;
    }

    public NotaRecebidaBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public NotaRecebidaBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public NotaRecebidaBuilder comCredito(Credito credito) {
        this.credito = credito;
        return this;
    }

    public NotaRecebidaBuilder comCheque(List<Parcela> parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public NotaRecebidaBuilder comMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
        return this;
    }

    public NotaRecebidaBuilder comParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public NotaRecebida construir() throws DadoInvalidoException {
        return new NotaRecebida(id, pessoa, operacao, itensRecebidos, formaDeRecebimento, listaDePreco, valoresAVista, baixas, emissao, cancelada, credito, parcelas, moedaPadrao);
    }

}
