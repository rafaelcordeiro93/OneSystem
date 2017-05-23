/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.builder.ItemDeNotaBV;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaRecebidaBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemDeNota> itens;
    private ListaDePreco listaDePreco;
    private FormaDeRecebimento formaDeRecebimento;
    private List<Cobranca> cobrancas;
    private Moeda moedaPadrao;
    private List<ValorPorCotacao> valorPorCotacao;
    private BigDecimal totalEmDinheiro;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private BigDecimal aFaturar;
    private Nota notaDeOrigem;

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

    public NotaRecebidaBuilder comItens(List<ItemDeNota> itens) throws DadoInvalidoException {
        if (id == null) {
            if (itens != null && !itens.isEmpty()) {
                for (ItemDeNota i : itens) {
                    itens.set(itens.indexOf(i), new ItemDeNotaBV(i).construir());
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Itens_Devem_Ser_Informados"));
            }
            this.itens = itens;
        } else {
            throw new FDadoInvalidoException(new BundleUtil().getMessage("Nao_Constroi_Itens_Nota_Existente"));
        }
        return this;
    }

    public NotaRecebidaBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public NotaRecebidaBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public NotaRecebidaBuilder comCheque(List<Cobranca> parcelas) {
        this.cobrancas = parcelas;
        return this;
    }

    public NotaRecebidaBuilder comMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
        return this;
    }

    public NotaRecebidaBuilder comCobrancas(List<Cobranca> cobrancas) throws DadoInvalidoException {
        if (id == null) {
            if (cobrancas != null && !cobrancas.isEmpty()) {
                for (Cobranca i : cobrancas) {
                    if (i.getId() != null) {
                        if (i instanceof Cheque) {
                            cobrancas.set(cobrancas.indexOf(i), new CobrancaBV(i).construirCheque());
                        }
                        if (i instanceof BoletoDeCartao) {
                            cobrancas.set(cobrancas.indexOf(i), new CobrancaBV(i).construirBoletoDeCartao());
                        }
                        if (i instanceof Titulo) {
                            cobrancas.set(cobrancas.indexOf(i), new CobrancaBV(i).construirTitulo());
                        }
                    }
                }
            }
            this.cobrancas = cobrancas;
        }
        return this;
    }

    public NotaRecebidaBuilder comValorPorCotacao(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
        return this;
    }

    public NotaRecebidaBuilder comTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
        return this;
    }

    public NotaRecebidaBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public NotaRecebidaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public NotaRecebidaBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public NotaRecebidaBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public NotaRecebidaBuilder comAFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
        return this;
    }

    public NotaRecebidaBuilder comNotaDeOrigem(Nota notaDeOrigem) {
        this.notaDeOrigem = notaDeOrigem;
        return this;
    }

    public NotaRecebida construir() throws DadoInvalidoException {
        return new NotaRecebida(id, pessoa, operacao, itens, formaDeRecebimento, listaDePreco, cobrancas, moedaPadrao, valorPorCotacao, desconto, acrescimo, despesaCobranca, frete, aFaturar, totalEmDinheiro, notaDeOrigem);
    }

}
