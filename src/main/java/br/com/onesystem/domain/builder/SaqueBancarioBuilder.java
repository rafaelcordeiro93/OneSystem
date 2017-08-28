/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class SaqueBancarioBuilder {

    private Long id;
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;
    private Date emissao;
    private String observacao;
    private Filial filial;

    public SaqueBancarioBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public SaqueBancarioBuilder comOrigem(Conta origem) {
        this.origem = origem;
        return this;
    }

    public SaqueBancarioBuilder comDestino(Conta destino) {
        this.destino = destino;
        return this;
    }

    public SaqueBancarioBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public SaqueBancarioBuilder comValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
        return this;
    }

    public SaqueBancarioBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public SaqueBancarioBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public SaqueBancarioBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public SaqueBancarioBuilder comFilial(Filial filial) {
        this.filial = filial;
        return this;
    }

    public SaqueBancario construir() throws DadoInvalidoException {
        return new SaqueBancario(id, emissao, origem, destino, valor, valorConvertido, baixas, observacao, filial);
    }

}
