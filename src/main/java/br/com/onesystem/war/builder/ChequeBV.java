package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.builder.ChequeBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoSituacaoCheque;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChequeBV implements Serializable {

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
    private TipoSituacaoCheque tipoSituacao;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;

    public ChequeBV(Cheque chequeelecionada) {
        this.id = chequeelecionada.getId();
        this.venda = chequeelecionada.getVenda();
        this.numeroParcela = chequeelecionada.getNumeroParcela();
        this.valor = chequeelecionada.getValor();
        this.emissao = chequeelecionada.getEmissao();
        this.vencimento = chequeelecionada.getVencimento();
        this.banco = chequeelecionada.getBanco();
        this.agencia = chequeelecionada.getAgencia();
        this.conta = chequeelecionada.getConta();
        this.numeroCheque = chequeelecionada.getNumeroCheque();
        this.tipoSituacao = chequeelecionada.getTipoSituacao();
        this.multas = chequeelecionada.getMultas();
        this.juros = chequeelecionada.getJuros();
        this.descontos = chequeelecionada.getDescontos();
        this.emitente = chequeelecionada.getEmitente();
        this.observacao = chequeelecionada.getObservacao();
    }

    public ChequeBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotaEmitida getVenda() {
        return venda;
    }

    public void setVenda(NotaEmitida venda) {
        this.venda = venda;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public TipoSituacaoCheque getTipoSituacao() {
        return tipoSituacao;
    }

    public void setTipoSituacao(TipoSituacaoCheque tipoSituacao) {
        this.tipoSituacao = tipoSituacao;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public void setMultas(BigDecimal multas) {
        this.multas = multas;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getDescontos() {
        return descontos;
    }

    public void setDescontos(BigDecimal descontos) {
        this.descontos = descontos;
    }

    public String getEmitente() {
        return emitente;
    }

    public void setEmitente(String emitente) {
        this.emitente = emitente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cheque construir() throws DadoInvalidoException {
        return new ChequeBuilder().comVenda(venda).comNumeroParcelas(numeroParcela).comValor(valor).comEmissao(emissao).comVencimento(vencimento)
                .comBanco(banco).comAgencia(agencia).comConta(conta).comNumeroCheque(numeroCheque).comTipoSituacao(tipoSituacao).comMultas(multas)
                .comJuros(juros).comDesconto(descontos).comEmitente(emitente).comObservacao(observacao).construir();
    }

    public Cheque construirComID() throws DadoInvalidoException {
        return new ChequeBuilder().comID(id).comVenda(venda).comNumeroParcelas(numeroParcela).comValor(valor).comEmissao(emissao).comVencimento(vencimento)
                .comBanco(banco).comAgencia(agencia).comConta(conta).comNumeroCheque(numeroCheque).comTipoSituacao(tipoSituacao).comMultas(multas)
                .comJuros(juros).comDesconto(descontos).comEmitente(emitente).comObservacao(observacao).construir();
    }
}
