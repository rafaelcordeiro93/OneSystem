/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Inject;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class GeradorDeBaixaDeTipoCobrancaFixa implements Serializable{

    private TipoDeCobranca tipoDeCobranca;
    private TipoReceita tipoReceita;
    private TipoDespesa tipoDespesa;
    private BundleUtil msg = new BundleUtil();

    @Inject
    private ConfiguracaoContabil conf;

    public void geraBaixas(TipoDeCobranca tipoDeCobranca) throws DadoInvalidoException {
        this.tipoDeCobranca = tipoDeCobranca;
        String tipo = tipoDeCobranca.getTipoDocumento();

        if (tipoDeCobranca.getJuros() != null && tipoDeCobranca.getJuros().compareTo(BigDecimal.ZERO) > 0) {
            tipoDeCobranca.adiciona(getJuros(tipo));
        }
        if (tipoDeCobranca.getMulta() != null && tipoDeCobranca.getMulta().compareTo(BigDecimal.ZERO) > 0) {
            tipoDeCobranca.adiciona(getMulta(tipo));
        }
        if (tipoDeCobranca.getDesconto() != null && tipoDeCobranca.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
            tipoDeCobranca.adiciona(getDesconto(tipo));
        }
        tipoDeCobranca.adiciona(getValor(tipo));

        //Atualiza situação da Cobrança 
        tipoDeCobranca.getCobranca().atualizaSituacao();
    }

    private Baixa getValor(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getValor()).comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira());

        if (tipoDeCobranca.getMovimento() instanceof Recebimento) {
            builder.comReceita(tipoReceita).comHistorico(msg.getMessage("Recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(tipoDespesa).comHistorico(msg.getMessage("Pagamento_de") + " " + tipo + getHistorico());
        }
        return builder.construir();
    }

    private Baixa getDesconto(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getDesconto())
                .comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA ? OperacaoFinanceira.SAIDA : OperacaoFinanceira.ENTRADA);

        if (tipoDeCobranca.getMovimento() instanceof Recebimento) {
            builder.comDespesa(conf.getDespesaDeDescontosConcedidos()).comHistorico(msg.getMessage("Desconto_concedido_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comReceita(conf.getReceitaDeDescontosObtidos()).comHistorico(msg.getMessage("Desconto_recebido_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getMulta(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getMulta()).comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira());

        if (tipoDeCobranca.getMovimento() instanceof Recebimento) {
            builder.comReceita(conf.getReceitaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getJuros(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaFixaBuilder();
        builder.comValor(tipoDeCobranca.getJuros()).comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira());

        if (tipoDeCobranca.getMovimento() instanceof Recebimento) {
            builder.comReceita(conf.getReceitaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private BaixaBuilder getCobrancaFixaBuilder() {
        BaixaBuilder baixaBuilder = new BaixaBuilder();
        baixaBuilder.comFilial(tipoDeCobranca.getMovimento().getFilial()).comEmissao(tipoDeCobranca.getMovimento().getEmissao()).comCaixa(tipoDeCobranca.getMovimento().getCaixa());

        return baixaBuilder.
                comCotacao(tipoDeCobranca.getCotacao()).comTipoDeCobranca(tipoDeCobranca).
                comPessoa(tipoDeCobranca.getCobranca().getPessoa());
    }

    private String getHistorico() {
        String pessoa = "";
        if (tipoDeCobranca.getCobranca().getPessoa() != null) {
            pessoa = " " + msg.getMessage("de") + " " + tipoDeCobranca.getCobranca().getPessoa().getNome();
        }

        String id = tipoDeCobranca.getCobranca().getId() == null ? "" : " " + tipoDeCobranca.getCobranca().getId().toString();
        return id + pessoa;
    }

}
