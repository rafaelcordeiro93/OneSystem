/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.CambioEmpresa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CambioEmpresaBuilder {

    private Long id;
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;
    private Date emissao;
    private String observacao;

    public CambioEmpresaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CambioEmpresaBuilder comOrigem(Conta origem) {
        this.origem = origem;
        return this;
    }

    public CambioEmpresaBuilder comDestino(Conta destino) {
        this.destino = destino;
        return this;
    }

    public CambioEmpresaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public CambioEmpresaBuilder comValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
        return this;
    }

    public CambioEmpresaBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public CambioEmpresaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public CambioEmpresaBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public CambioEmpresa construir() throws DadoInvalidoException {
        return new CambioEmpresa(id, emissao, origem, destino, valor, valorConvertido, baixas, observacao);
    }

}
