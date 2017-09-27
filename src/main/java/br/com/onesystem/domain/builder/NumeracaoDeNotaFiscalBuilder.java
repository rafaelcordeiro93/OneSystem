/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Cordeiro
 */
public class NumeracaoDeNotaFiscalBuilder {

    private Long id;
    private Integer numeroNF;
    private LoteNotaFiscal loteNotaFiscal;
    private Filial filial;

    public NumeracaoDeNotaFiscalBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public NumeracaoDeNotaFiscalBuilder comNumeroNF(Integer numeroNF) {
        this.numeroNF = numeroNF;
        return this;
    }

    public NumeracaoDeNotaFiscalBuilder comLoteNotaFiscal(LoteNotaFiscal loteNotaFiscal) {
        this.loteNotaFiscal = loteNotaFiscal;
        return this;
    }

    public NumeracaoDeNotaFiscalBuilder comFilial(Filial filial) {
        this.filial = filial;
        return this;
    }

    public NumeracaoDeNotaFiscal construir() throws DadoInvalidoException {
        return new NumeracaoDeNotaFiscal(id, numeroNF, loteNotaFiscal, filial);
    }

}
