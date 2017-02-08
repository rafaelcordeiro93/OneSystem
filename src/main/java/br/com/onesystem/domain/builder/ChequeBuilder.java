package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.ValoresAVista;
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
    private ValoresAVista formaDeRecebimentoOuPagamento;
    private Cotacao cotacao;

    public ChequeBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ChequeBuilder comNotaEmitida(NotaEmitida venda) {
        this.venda = venda;
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

    public ChequeBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ChequeBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public ChequeBuilder comFormaDeRecebimentoOuPagamento(ValoresAVista formaDeRecebimentoOuPagamento) {
        this.formaDeRecebimentoOuPagamento = formaDeRecebimentoOuPagamento;
        return this;
    }

    public Cheque construir() throws DadoInvalidoException {
        return new Cheque(id, venda, valor, emissao, vencimento, banco, agencia, conta, numeroCheque, tipoSituacao, multas, juros, descontos, emitente, observacao, formaDeRecebimentoOuPagamento, cotacao);
    }

}
