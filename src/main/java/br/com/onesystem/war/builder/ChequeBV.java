package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.builder.ChequeBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChequeBV implements Serializable {

    private Long id;
    private Nota nota;
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
    private TipoLancamento tipoLancamento;
    private Pessoa pessoa;
    private OperacaoFinanceira operacaoFinanceira;
    private Boolean entrada;

    public ChequeBV(Cheque c) {
        this.id = c.getId();
        this.nota = c.getNota();
        this.valor = c.getValor();
        this.emissao = c.getEmissao();
        this.vencimento = c.getVencimento();
        this.banco = c.getBanco();
        this.agencia = c.getAgencia();
        this.conta = c.getConta();
        this.numeroCheque = c.getNumeroCheque();
        this.tipoSituacao = c.getTipoSituacao();
        this.multas = c.getMultas();
        this.juros = c.getJuros();
        this.descontos = c.getDescontos();
        this.emitente = c.getEmitente();
        this.historico = c.getHistorico();
        this.cotacao = c.getCotacao();
        this.valoresAVista = c.getValoresAVista();
        this.tipoLancamento = c.getTipoLancamento();
        this.pessoa = c.getPessoa();
        this.operacaoFinanceira = c.getOperacaoFinanceira();
    }

    public ChequeBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nota getVenda() {
        return nota;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public void setVenda(Nota venda) {
        this.nota = venda;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }      

    public Cheque construir() throws DadoInvalidoException {
        return new ChequeBuilder().comNota(nota).comValor(valor).comEmissao(emissao).comVencimento(vencimento).comPessoa(pessoa)
                .comBanco(banco).comAgencia(agencia).comConta(conta).comNumeroCheque(numeroCheque).comTipoSituacao(tipoSituacao).comMultas(multas)
                .comJuros(juros).comDesconto(descontos).comEmitente(emitente).comCotacao(cotacao).comObservacao(historico).comOperacaoFinanceira(operacaoFinanceira)
                .comValoresAVista(valoresAVista).comTipoLancamento(tipoLancamento).comEntrada(entrada).construir();
    }

    public Cheque construirComID() throws DadoInvalidoException {
        return new ChequeBuilder().comID(id).comNota(nota).comValor(valor).comEmissao(emissao).comVencimento(vencimento).comPessoa(pessoa)
                .comBanco(banco).comAgencia(agencia).comConta(conta).comNumeroCheque(numeroCheque).comTipoSituacao(tipoSituacao).comMultas(multas)
                .comJuros(juros).comDesconto(descontos).comEmitente(emitente).comCotacao(cotacao).comObservacao(historico).comOperacaoFinanceira(operacaoFinanceira)
                .comValoresAVista(valoresAVista).comTipoLancamento(tipoLancamento).comEntrada(entrada).construir();
    }
}
