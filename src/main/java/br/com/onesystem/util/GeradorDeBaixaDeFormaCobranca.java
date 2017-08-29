/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import java.math.BigDecimal;

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
    }

    private Baixa getValor(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getValor()).comOperacaoFinanceira(formaDeCobranca.getOperacaoFinanceira());

        if (formaDeCobranca.getRecebimento() == null) {//pagamento
            if (formaDeCobranca.getCobranca().getNota() != null) {
                builder.comReceita(formaDeCobranca.getCobranca().getNota().getOperacao().getVendaAPrazo());
            }
            builder.comHistorico(msg.getMessage("Recebimento_de") + " " + forma + getHistorico());
            if (forma.equals(new BundleUtil().getLabel("Cheque"))) {
                Cheque ch = (Cheque) formaDeCobranca.getCobranca();
                builder.comHistorico(new BundleUtil().getMessage("Abatimento_de") + " " + forma + getHistorico()).comDataCompensacao(ch.getCompensacao()).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO);
            } else {
                builder.comHistorico(new BundleUtil().getMessage("Pagamento_de") + " " + forma + getHistorico());
            }
        } else {//recebimento
            if (formaDeCobranca.getCobranca().getNota() != null) {
                builder.comDespesa(formaDeCobranca.getCobranca().getNota().getOperacao().getCompraAPrazo());
            }
            if (forma.equals(new BundleUtil().getLabel("Cheque"))) {
                builder.comHistorico(msg.getMessage("Abatimento_de") + " " + forma + getHistorico()).comEstadoDeBaixa(EstadoDeBaixa.EFETIVADO);
            } else {
                builder.comHistorico(msg.getMessage("Pagamento_de") + " " + forma + getHistorico());
            }
        }
        return builder.construir();
    }

    private Baixa getDesconto(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getDesconto())
                .comOperacaoFinanceira(formaDeCobranca.getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA ? OperacaoFinanceira.SAIDA : OperacaoFinanceira.ENTRADA);

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comDespesa(conf.getDespesaDeDescontosConcedidos()).comHistorico(msg.getMessage("Desconto_concedido_sobre_recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comReceita(conf.getReceitaDeDescontosObtidos()).comHistorico(msg.getMessage("Desconto_recebido_sobre_pagamento_de") + " " + forma + getHistorico());
        }
        return builder.construir();
    }

    private Baixa getMulta(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getMulta()).comOperacaoFinanceira(formaDeCobranca.getOperacaoFinanceira());

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comReceita(conf.getReceitaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_pagamento_de") + " " + forma + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getJuros(String forma) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(formaDeCobranca.getJuros()).comOperacaoFinanceira(formaDeCobranca.getOperacaoFinanceira());

        if (formaDeCobranca.getRecebimento() == null) {
            builder.comReceita(conf.getReceitaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_recebimento_de") + " " + forma + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_pagamento_de") + " " + forma + getHistorico());
        }

        return builder.construir();
    }

    private BaixaBuilder getCobrancaBuilder() {
        BaixaBuilder baixaBuilder = new BaixaBuilder();
        if (formaDeCobranca.getRecebimento() != null) {
            baixaBuilder.comFilial(formaDeCobranca.getRecebimento().getFilial()).comEmissao(formaDeCobranca.getRecebimento().getEmissao()).comCaixa(formaDeCobranca.getRecebimento().getCaixa());
        } else {
            baixaBuilder.comFilial(formaDeCobranca.getRecebimento().getFilial()).comEmissao(formaDeCobranca.getPagamento().getEmissao()).comCaixa(formaDeCobranca.getPagamento().getCaixa());
        }

        return baixaBuilder.
                comCotacao(formaDeCobranca.getCotacao()).
                comCobranca(formaDeCobranca.getCobranca()).comFormaDeCobranca(formaDeCobranca).
                comPessoa(formaDeCobranca.getCobranca().getPessoa());
    }

    private String getHistorico() {
        String str = formaDeCobranca.getCobranca().getId() == null ? "" : formaDeCobranca.getCobranca().getId().toString() + " ";
        return " " + str + msg.getMessage("de") + " " + formaDeCobranca.getCobranca().getPessoa().getNome();
    }
}
