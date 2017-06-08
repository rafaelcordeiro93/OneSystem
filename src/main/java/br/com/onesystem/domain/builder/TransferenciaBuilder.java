/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class TransferenciaBuilder {

    private Long id;
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;
    private Date emissao;

    public TransferenciaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public TransferenciaBuilder comOrigem(Conta origem) {
        this.origem = origem;
        return this;
    }

    public TransferenciaBuilder comDestino(Conta destino) {
        this.destino = destino;
        return this;
    }

    public TransferenciaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public TransferenciaBuilder comValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
        return this;
    }

    public TransferenciaBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public TransferenciaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public Transferencia construir() throws DadoInvalidoException {
        return new Transferencia(id, origem, destino, valor, valorConvertido, baixas, emissao);
    }

}
