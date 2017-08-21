/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.domain.TabelaDeTributacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoPessoa;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class SituacaoFiscalBuilder {

    private Long id;
    private Estado estado;
    private TipoPessoa tipoPessoa;
    private Operacao operacao;
    private GrupoFiscal grupoFiscal;
    private TabelaDeTributacao tabelaDeTributacao;
    private Integer sequencia;
    private Cfop cfop;
    private String observacao;

    public SituacaoFiscalBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public SituacaoFiscalBuilder comSequencia(Integer sequencia) {
        this.sequencia = sequencia;
        return this;
    }

    public SituacaoFiscalBuilder comCFOP(Cfop cfop) {
        this.cfop = cfop;
        return this;
    }

    public SituacaoFiscalBuilder comEstado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public SituacaoFiscalBuilder comTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
        return this;
    }

    public SituacaoFiscalBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public SituacaoFiscalBuilder comGrupoFiscal(GrupoFiscal grupoFiscal) {
        this.grupoFiscal = grupoFiscal;
        return this;
    }

    public SituacaoFiscalBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public SituacaoFiscalBuilder comTabelaDeTributacao(TabelaDeTributacao tabelaDeTributacao) {
        this.tabelaDeTributacao = tabelaDeTributacao;
        return this;
    }

    public SituacaoFiscal construir() throws DadoInvalidoException {
        return new SituacaoFiscal(id, sequencia, operacao, grupoFiscal, tabelaDeTributacao, estado, tipoPessoa, cfop, observacao);
    }

}
