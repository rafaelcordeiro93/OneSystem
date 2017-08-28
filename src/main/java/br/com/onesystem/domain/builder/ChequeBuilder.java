package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ChequeBuilder {

    private Long id;
    private BigDecimal valor;
    private Date emissao = new Date();
    private Date vencimento = new Date();
    private Banco banco;
    private String agencia;
    private String conta;
    private String numeroCheque;
    private EstadoDeCheque tipoSituacao;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;
    private Cotacao cotacao;
    private TipoLancamento tipoLancamento;
    private Pessoa pessoa;
    private List<Baixa> baixas;
    private OperacaoFinanceira operacaoFinanceira;
    private Boolean entrada;
    private Nota nota;
    private String historico;
    private SituacaoDeCobranca situacaoDeCobranca;
    private Date compensacao;
    private Filial filial;
    private Integer parcela;

    public ChequeBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ChequeBuilder comNota(Nota nota) {
        this.nota = nota;
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

    public ChequeBuilder comTipoSituacao(EstadoDeCheque tipoSituacao) {
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

    public ChequeBuilder comTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
        return this;
    }

    public ChequeBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public ChequeBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public ChequeBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public ChequeBuilder comEntrada(Boolean entrada) {
        this.entrada = entrada;
        return this;
    }

    public ChequeBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public ChequeBuilder comSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
        return this;
    }

    public ChequeBuilder comCompensacao(Date compensacao) {
        this.compensacao = compensacao;
        return this;
    }

    public ChequeBuilder comFilial(Filial filial){
        this.filial = filial;
        return this;
    }
    
    public ChequeBuilder comParcela(Integer parcela){
        this.parcela = parcela;
        return this;
    }
    
    public Cheque construir() throws DadoInvalidoException {
        return new Cheque(id, nota, valor, emissao, vencimento, banco, agencia, conta, numeroCheque, tipoSituacao, multas, juros, descontos, emitente, operacaoFinanceira, historico, cotacao, tipoLancamento, pessoa, baixas, entrada, situacaoDeCobranca, compensacao, filial, parcela);
    }

}
