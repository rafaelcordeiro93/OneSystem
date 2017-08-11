/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class DepositoBancarioBuilder {

    private Long id;
    private Conta origem;
    private Conta destino;
    private BigDecimal valor;
    private BigDecimal valorConvertido;
    private List<Baixa> baixas;
    private List<Cheque> cheques;
    private Date emissao;
    private Date compensacao;
    private String observacao;
    private TipoLancamentoBancario tipoLancamentoBancario;
    private boolean estornado;
    private Long idRelacaoEstorno;

    public DepositoBancarioBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public DepositoBancarioBuilder comOrigem(Conta origem) {
        this.origem = origem;
        return this;
    }

    public DepositoBancarioBuilder comDestino(Conta destino) {
        this.destino = destino;
        return this;
    }

    public DepositoBancarioBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public DepositoBancarioBuilder comValorConvertido(BigDecimal valorConvertido) {
        this.valorConvertido = valorConvertido;
        return this;
    }

    public DepositoBancarioBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public DepositoBancarioBuilder comCheques(List<Cheque> cheques) {
        this.cheques = cheques;
        return this;
    }

    public DepositoBancarioBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public DepositoBancarioBuilder comCompensacao(Date compensacao) {
        this.compensacao = compensacao;
        return this;
    }

    public DepositoBancarioBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public DepositoBancarioBuilder comTipoLancamentoBancario(TipoLancamentoBancario tipoLancamentoBancario) {
        this.tipoLancamentoBancario = tipoLancamentoBancario;
        return this;
    }

    public DepositoBancarioBuilder comEstornado(boolean estornado) {
        this.estornado = estornado;
        return this;
    }

    public DepositoBancarioBuilder comIdRelacaoEstorno(Long idEstorno) {
        this.idRelacaoEstorno = idEstorno;
        return this;
    }

    public DepositoBancario construir() throws DadoInvalidoException {
        return new DepositoBancario(id, emissao, compensacao, origem, destino, valor, valorConvertido, baixas, cheques, observacao, tipoLancamentoBancario, estornado, idRelacaoEstorno);
    }

}
