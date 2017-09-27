/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EspecieDeNotaFiscal;
import br.com.onesystem.valueobjects.ModeloDeNotaFiscal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Cordeiro
 */
public class LoteNotaFiscalBuilder {

    private Long id;
    private String nome;
    private ModeloDeNotaFiscal modelo;
    private EspecieDeNotaFiscal especie;
    private Long serie;
    private Date dataDeInicio;
    private Date dataDeFim;
    private String observacao;
    private List<NumeracaoDeNotaFiscal> numeracaoDeNotaFiscal;

    public LoteNotaFiscalBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public LoteNotaFiscalBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LoteNotaFiscalBuilder comModelo(ModeloDeNotaFiscal modelo) {
        this.modelo = modelo;
        return this;
    }

    public LoteNotaFiscalBuilder comEspecie(EspecieDeNotaFiscal especie) {
        this.especie = especie;
        return this;
    }

    public LoteNotaFiscalBuilder comSerie(Long serie) {
        this.serie = serie;
        return this;
    }

    public LoteNotaFiscalBuilder comDataDeInicio(Date dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
        return this;
    }

    public LoteNotaFiscalBuilder comDataDeFim(Date dataDeFim) {
        this.dataDeFim = dataDeFim;
        return this;
    }

    public LoteNotaFiscalBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public LoteNotaFiscalBuilder comNumeracaoDeNotaFiscal(List<NumeracaoDeNotaFiscal> numeracaoDeNotaFiscal) {
        this.numeracaoDeNotaFiscal = numeracaoDeNotaFiscal;
        return this;
    }

    public LoteNotaFiscal construir() throws DadoInvalidoException {
        return new LoteNotaFiscal(id, nome, modelo, especie, serie, dataDeInicio, dataDeFim, observacao, numeracaoDeNotaFiscal);
    }

}
