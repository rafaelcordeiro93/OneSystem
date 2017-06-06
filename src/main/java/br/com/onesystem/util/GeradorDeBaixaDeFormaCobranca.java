/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class GeradorDeBaixaDeFormaCobranca {

    private FormaDeCobranca formaDeCobranca;
    private BundleUtil msg = new BundleUtil();
    private ConfiguracaoContabil conf = new ConfiguracaoContabilService().buscar();

    public GeradorDeBaixaDeFormaCobranca(FormaDeCobranca formaDeCobranca) throws DadoInvalidoException {
        this.formaDeCobranca = formaDeCobranca;
    }

    public void geraBaixas() throws DadoInvalidoException {
        if (formaDeCobranca.getJuros() != null && formaDeCobranca.getJuros().compareTo(BigDecimal.ZERO) > 0) {
            formaDeCobranca.getCobranca().adiciona(getJuros(formaDeCobranca.getTipoDocumento()));
        }
        if (formaDeCobranca.getMulta() != null && formaDeCobranca.getMulta().compareTo(BigDecimal.ZERO) > 0) {
            formaDeCobranca.getCobranca().adiciona(getMulta(formaDeCobranca.getTipoDocumento()));
        }
        if (formaDeCobranca.getDesconto() != null && formaDeCobranca.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
            formaDeCobranca.getCobranca().adiciona(getDesconto(formaDeCobranca.getTipoDocumento()));
        }
        formaDeCobranca.getCobranca().adiciona(getValor(formaDeCobranca.getTipoDocumento()));

        if (formaDeCobranca.getCobranca() instanceof Titulo) {
            Titulo titulo = (Titulo) formaDeCobranca.getCobranca();
            titulo.atualizaSaldo(formaDeCobranca.getValor());
        }
    }

    private Baixa getValor(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getValor()).comOperacaoFinanceira(formaDeCobranca.getCobranca().getOperacaoFinanceira());

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comReceita(formaDeCobranca.getCobranca().getNota().getOperacao().getVendaAPrazo()).comHistorico(msg.getMessage("Recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comDespesa(formaDeCobranca.getCobranca().getNota().getOperacao().getCompraAPrazo()).comHistorico(msg.getMessage("Pagamento_de") + " " + forma + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getDesconto(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getDesconto())
                .comOperacaoFinanceira(formaDeCobranca.getCobranca().getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA ? OperacaoFinanceira.SAIDA : OperacaoFinanceira.ENTRADA);

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comDespesa(conf.getDespesaDeDescontosConcedidos()).comHistorico(msg.getMessage("Desconto_concedido_sobre_recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comReceita(conf.getReceitaDeDescontosObtidos()).comHistorico(msg.getMessage("Desconto_recebido_sobre_pagamento_de") + " " + forma + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getMulta(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getMulta()).comOperacaoFinanceira(formaDeCobranca.getCobranca().getOperacaoFinanceira());

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comReceita(conf.getReceitaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_pagamento_de") + " " + forma + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getJuros(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getJuros()).comOperacaoFinanceira(formaDeCobranca.getCobranca().getOperacaoFinanceira());

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comReceita(conf.getReceitaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_pagamento_de") + " " + forma + getHistorico());
        }

        return builder.construir();
    }

    private BaixaBuilder getCobrancaBuilder() {
        Date emissao = new Date();
        if (formaDeCobranca.getRecebimento() != null) {
            emissao = formaDeCobranca.getRecebimento().getEmissao();
        } else {
            emissao = formaDeCobranca.getPagamento().getEmissao();
        }

        return new BaixaBuilder().
                comCotacao(formaDeCobranca.getCotacao()).comEmissao(emissao).
                comCobranca(formaDeCobranca.getCobranca()).comFormaDeCobranca(formaDeCobranca).
                comPessoa(formaDeCobranca.getCobranca().getPessoa());
    }

    private String getHistorico() {
        return " " + formaDeCobranca.getCobranca().getId() + " " + msg.getMessage("de") + " " + formaDeCobranca.getCobranca().getPessoa().getNome();
    }

}
