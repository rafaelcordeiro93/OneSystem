package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.builder.ChequeBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChequeBV implements Serializable {

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
    private String historico;
    private Cotacao cotacao;
    private ValoresAVista valoresAVista;

    public ChequeBV(Cheque chequeSelecionado) {
        this.id = chequeSelecionado.getId();
        this.venda = chequeSelecionado.getNotaEmitida();
        this.valor = chequeSelecionado.getValor();
        this.emissao = chequeSelecionado.getEmissao();
        this.vencimento = chequeSelecionado.getVencimento();
        this.banco = chequeSelecionado.getBanco();
        this.agencia = chequeSelecionado.getAgencia();
        this.conta = chequeSelecionado.getConta();
        this.numeroCheque = chequeSelecionado.getNumeroCheque();
        this.tipoSituacao = chequeSelecionado.getTipoSituacao();
        this.multas = chequeSelecionado.getMultas();
        this.juros = chequeSelecionado.getJuros();
        this.descontos = chequeSelecionado.getDescontos();
        this.emitente = chequeSelecionado.getEmitente();
        this.historico = chequeSelecionado.getHistorico();
        this.cotacao = chequeSelecionado.getCotacao();
        this.valoresAVista = chequeSelecionado.getValoresAVista();
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

    public SituacaoDeCheque getTipoSituacao() {
        return tipoSituacao;
    }

    public void setTipoSituacao(SituacaoDeCheque tipoSituacao) {
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public ValoresAVista getValoresAVista() {
        return valoresAVista;
    }

    public void setValoresAVista(ValoresAVista valoresAVista) {
        this.valoresAVista = valoresAVista;
    }
    
    public Cheque construir() throws DadoInvalidoException {
        return new ChequeBuilder().comNotaEmitida(venda).comValor(valor).comEmissao(emissao).comVencimento(vencimento)
                .comBanco(banco).comAgencia(agencia).comConta(conta).comNumeroCheque(numeroCheque).comTipoSituacao(tipoSituacao).comMultas(multas)
                .comJuros(juros).comDesconto(descontos).comEmitente(emitente).comCotacao(cotacao).comObservacao(historico)
                .comValoresAVista(valoresAVista).construir();
    }

    public Cheque construirComID() throws DadoInvalidoException {
        return new ChequeBuilder().comID(id).comNotaEmitida(venda).comValor(valor).comEmissao(emissao).comVencimento(vencimento)
                .comBanco(banco).comAgencia(agencia).comConta(conta).comNumeroCheque(numeroCheque).comTipoSituacao(tipoSituacao).comMultas(multas)
                .comJuros(juros).comDesconto(descontos).comEmitente(emitente).comCotacao(cotacao).comObservacao(historico)
                .comValoresAVista(valoresAVista).construir();
    }
}
