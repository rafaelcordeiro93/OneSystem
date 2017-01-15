package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class ChequeBuilder {

    private Long id;
    private NotaEmitida venda;
    private Integer numeroParcela;
    private BigDecimal valor;
    private Date emissao = new Date();
    private Date vencimento = new Date();
    private Banco banco;
    private String agencia;
    private String conta;
    private String numeroCheque;
    private SituacaoDeCheque tipoSituacao;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;

    public ChequeBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ChequeBuilder comVenda(NotaEmitida venda) {
        this.venda = venda;
        return this;
    }

    public ChequeBuilder comNumeroParcelas(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
        return this;
    }

    public ChequeBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ChequeBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public ChequeBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public ChequeBuilder comBanco(Banco banco) {
        this.banco = banco;
        return this;
    }

    public ChequeBuilder comAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public ChequeBuilder comConta(String conta) {
        this.conta = conta;
        return this;
    }

    public ChequeBuilder comNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
        return this;
    }

    public ChequeBuilder comTipoSituacao(SituacaoDeCheque tipoSituacao) {
        this.tipoSituacao = tipoSituacao;
        return this;
    }

    public ChequeBuilder comMultas(BigDecimal multas) {
        this.multas = multas;
        return this;
    }

    public ChequeBuilder comJuros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public ChequeBuilder comDesconto(BigDecimal descontos) {
        this.descontos = descontos;
        return this;
    }

    public ChequeBuilder comEmitente(String emitente) {
        this.emitente = emitente;
        return this;
    }

    public ChequeBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public Cheque construir() throws DadoInvalidoException {
        return new Cheque(id, venda, numeroParcela, valor, emissao, vencimento, banco, agencia, conta, numeroCheque, tipoSituacao, multas, juros, descontos, emitente, observacao);
    }

}
