/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
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
    private TipoLancamentoBancario tipoLancamentoBancario;
    private boolean estornado;
    private Long idRelacaoEstorno;

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

    public TransferenciaBuilder comTipoLancamentoBancario(TipoLancamentoBancario tipoLancamentoBancario) {
        this.tipoLancamentoBancario = tipoLancamentoBancario;
        return this;
    }

    public TransferenciaBuilder comEstornado(boolean estornado) {
        this.estornado = estornado;
        return this;
    }

    public TransferenciaBuilder comIdRelacaoEstorno(Long idRelacaoEstorno) {
        this.idRelacaoEstorno = idRelacaoEstorno;
        return this;
    }

    public Transferencia construir() throws DadoInvalidoException {
        return new Transferencia(id, origem, destino, valor, valorConvertido, baixas, emissao, tipoLancamentoBancario, estornado, idRelacaoEstorno);
    }

}
