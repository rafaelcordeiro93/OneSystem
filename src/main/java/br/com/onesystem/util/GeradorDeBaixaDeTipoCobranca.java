/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.context.FacesContext;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class GeradorDeBaixaDeTipoCobranca {

    private TipoDeCobranca tipoDeCobranca;
    private BundleUtil msg = new BundleUtil();
    private ConfiguracaoContabil conf = new ConfiguracaoContabilService().buscar();
    private boolean entrou = false;

    public GeradorDeBaixaDeTipoCobranca(TipoDeCobranca tipoDeCobranca) throws DadoInvalidoException {
        this.tipoDeCobranca = tipoDeCobranca;
    }

    public void geraBaixas() throws DadoInvalidoException {
        String tipo = tipoDeCobranca.getTipoDocumento();

        if (tipoDeCobranca.getCobranca() instanceof Cheque) {
            verificaBaixasExistentes(tipoDeCobranca.getCobranca());
        }
        if (entrou == false) {

            if (tipoDeCobranca.getJuros() != null && tipoDeCobranca.getJuros().compareTo(BigDecimal.ZERO) > 0) {
                tipoDeCobranca.getCobranca().adiciona(getJuros(tipo));
            }
            if (tipoDeCobranca.getMulta() != null && tipoDeCobranca.getMulta().compareTo(BigDecimal.ZERO) > 0) {
                tipoDeCobranca.getCobranca().adiciona(getMulta(tipo));
            }
            if (tipoDeCobranca.getDesconto() != null && tipoDeCobranca.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
                tipoDeCobranca.getCobranca().adiciona(getDesconto(tipo));
            }
            tipoDeCobranca.getCobranca().adiciona(getValor(tipo));

            if (tipoDeCobranca.getCobranca() instanceof Titulo) {
                Titulo titulo = (Titulo) tipoDeCobranca.getCobranca();
                titulo.atualizaSaldo(tipoDeCobranca.getValor());
            }

        }
        //Atualiza situação da Cobrança 
        tipoDeCobranca.getCobranca().atualizaSituacao();
    }

    private void verificaBaixasExistentes(Cobranca d) throws ConstraintViolationException, DadoInvalidoException {
        List<Baixa> listaBaixa = new BaixaDAO().ePorCobranca(d).listaDeResultados();
        entrou = false;
        if (listaBaixa.size() > 0) {
            Cheque ch = (Cheque) tipoDeCobranca.getCobranca();
            Caixa caixa = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
            for (Baixa b : listaBaixa) {
                BaixaBV bv = new BaixaBV(b);
                if (ch.getTipoLancamento() == TipoLancamento.EMITIDA) {
                    bv.setHistorico(new BundleUtil().getMessage("Pagamento_de") + " " + new BundleUtil().getLabel("Cheque") + getHistorico());
                } else {
                    bv.setHistorico(new BundleUtil().getMessage("Abatimento_de") + " " + new BundleUtil().getLabel("Cheque") + getHistorico());
                }
                bv.setDataCompensacao(ch.getCompensacao());
                bv.setEstado(EstadoDeBaixa.EFETIVADO);
                bv.setCaixa(caixa);
                new AtualizaDAO<>().atualiza(bv.construirComID());
            }
            entrou = true;
        }
    }

    private Baixa getValor(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(tipoDeCobranca.getValor()).comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira());
        if (tipoDeCobranca.getRecebimento() != null) {
            if (((CobrancaVariavel)tipoDeCobranca.getCobranca()).getNota() != null) {
                builder.comReceita(((CobrancaVariavel)tipoDeCobranca.getCobranca()).getNota().getOperacao().getVendaAPrazo());
            }
            builder.comHistorico(msg.getMessage("Recebimento_de") + " " + tipo + getHistorico());
        } else {
            if (((CobrancaVariavel)tipoDeCobranca.getCobranca()).getNota() != null) {
                builder.comDespesa(((CobrancaVariavel)tipoDeCobranca.getCobranca()).getNota().getOperacao().getCompraAPrazo());
            }
            builder.comHistorico(msg.getMessage("Pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getDesconto(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(tipoDeCobranca.getDesconto())
                .comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA ? OperacaoFinanceira.SAIDA : OperacaoFinanceira.ENTRADA);

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comDespesa(conf.getDespesaDeDescontosConcedidos()).comHistorico(msg.getMessage("Desconto_concedido_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comReceita(conf.getReceitaDeDescontosObtidos()).comHistorico(msg.getMessage("Desconto_recebido_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getMulta(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(tipoDeCobranca.getMulta()).comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira());

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comReceita(conf.getReceitaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeMultas()).comHistorico(msg.getMessage("Multa_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private Baixa getJuros(String tipo) throws DadoInvalidoException {
        BaixaBuilder builder = getCobrancaBuilder();
        builder.comValor(tipoDeCobranca.getJuros()).comOperacaoFinanceira(tipoDeCobranca.getCobranca().getOperacaoFinanceira());

        if (tipoDeCobranca.getRecebimento() != null) {
            builder.comReceita(conf.getReceitaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_recebimento_de") + " " + tipo + getHistorico());
        } else {
            builder.comDespesa(conf.getDespesaDeJuros()).comHistorico(msg.getMessage("Juros_sobre_pagamento_de") + " " + tipo + getHistorico());
        }

        return builder.construir();
    }

    private BaixaBuilder getCobrancaBuilder() {
        BaixaBuilder baixaBuilder = new BaixaBuilder();
        if (tipoDeCobranca.getRecebimento() != null) {
            baixaBuilder.comFilial(tipoDeCobranca.getRecebimento().getFilial()).comEmissao(tipoDeCobranca.getRecebimento().getEmissao()).comCaixa(tipoDeCobranca.getRecebimento().getCaixa());
        } else {
            baixaBuilder.comFilial(tipoDeCobranca.getPagamento().getFilial()).comEmissao(tipoDeCobranca.getPagamento().getEmissao()).comCaixa(tipoDeCobranca.getPagamento().getCaixa());
        }

        return baixaBuilder.
                comCotacao(tipoDeCobranca.getCotacao()).
                comCobranca(tipoDeCobranca.getCobranca()).comTipoDeCobranca(tipoDeCobranca).
                comPessoa(tipoDeCobranca.getCobranca().getPessoa());
    }

    private String getHistorico() {
        return " " + tipoDeCobranca.getCobranca().getId() + " " + msg.getMessage("de") + " " + tipoDeCobranca.getCobranca().getPessoa().getNome();
    }

}
