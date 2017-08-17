/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
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
public class GeradorDeBaixaDeTipoCobrancaFixa {

    private TipoDeCobranca tipoDeCobranca;
    private TipoReceita tipoReceita;
    private TipoDespesa tipoDespesa;
    private BundleUtil msg = new BundleUtil();
    private ConfiguracaoContabil conf = new ConfiguracaoContabilService().buscar();

    public GeradorDeBaixaDeTipoCobrancaFixa(TipoDeCobranca tipoDeCobranca) throws DadoInvalidoException {
        this.tipoDeCobranca = tipoDeCobranca;
    }

    public void geraBaixas() throws DadoInvalidoException {
        String tipo = tipoDeCobranca.getTipoDocumento();

        if (tipoDeCobranca.getJuros() != null && tipoDeCobranca.getJuros().compareTo(BigDecimal.ZERO) > 0) {
            tipoDeCobranca.getCobrancaFixa().adiciona(getJuros(tipo));
        }
        if (tipoDeCobranca.getMulta() != null && tipoDeCobranca.getMulta().compareTo(BigDecimal.ZERO) > 0) {
            tipoDeCobranca.getCobrancaFixa().adiciona(getMulta(tipo));
        }
        if (tipoDeCobranca.getDesconto() != null && tipoDeCobranca.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
            tipoDeCobranca.getCobrancaFixa().adiciona(getDesconto(tipo));
        }
        tipoDeCobranca.getCobrancaFixa().adiciona(getValor(tipo));

        //Atualiza situação da Cobrança 
        tipoDeCobranca.getCobrancaFixa().atualizaSituacao();
    }

    private Baixa getValor(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getValor()).comOperacaoFinanceira(tipoDeCobranca.getCobrancaFixa().getOperacaoFinanceira());

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comReceita(tipoReceita).comHistorico(msg.getMessage("Recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(tipoDespesa).comHistorico(msg.getMessage("Pagamento_de") + " " + tipo + getHistorico());
        } return builder.construir();
    }

    private Baixa getDesconto(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getDesconto())
                .comOperacaoFinanceira(tipoDeCobranca.getCobrancaFixa().getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA ? OperacaoFinanceira.SAIDA : OperacaoFinanceira.ENTRADA);

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comDespesa(conf.getDespesaDeDescontosConcedidos()).comHistorico(msg.getMessage("Desconto_concedido_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comReceita(conf.getReceitaDeDescontosObtidos()).comHistorico(msg.getMessage("Desconto_recebido_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getMulta(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getMulta()).comOperacaoFinanceira(tipoDeCobranca.getCobrancaFixa().getOperacaoFinanceira());

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comReceita(conf.getReceitaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getJuros(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getJuros()).comOperacaoFinanceira(tipoDeCobranca.getCobrancaFixa().getOperacaoFinanceira());

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comReceita(conf.getReceitaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private BaixaBuilder getCobrancaFixaBuilder() {
        BaixaBuilder baixaBuilder = new BaixaBuilder();
        if (tipoDeCobranca.getRecebimento() != null) {
            baixaBuilder.comEmissao(tipoDeCobranca.getRecebimento().getEmissao()).comCaixa(tipoDeCobranca.getRecebimento().getCaixa());
        } else {
            baixaBuilder.comEmissao(tipoDeCobranca.getPagamento().getEmissao()).comCaixa(tipoDeCobranca.getPagamento().getCaixa());
        }

        return baixaBuilder.
                comCotacao(tipoDeCobranca.getCotacao()).
                comCobrancaFixa(tipoDeCobranca.getCobrancaFixa()).comTipoDeCobranca(tipoDeCobranca).
                comPessoa(tipoDeCobranca.getCobrancaFixa().getPessoa());
    }

    private String getHistorico() {
        String pessoa = "";
        if (tipoDeCobranca.getCobrancaFixa().getPessoa() != null) {
            pessoa = " " + msg.getMessage("de") + " " + tipoDeCobranca.getCobrancaFixa().getPessoa().getNome();
        }

        String id = tipoDeCobranca.getCobrancaFixa().getId() == null ? "" : " " + tipoDeCobranca.getCobrancaFixa().getId().toString();
        return id + pessoa;
    }

}
