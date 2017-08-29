package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class CreditoBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private OperacaoFinanceira operacaoFinanceira;
    private Nota nota;
    private List<Baixa> baixas;
    private Cotacao cotacao;
    private Boolean entrada;
    private SituacaoDeCobranca situacaoDeCobranca;
    private Filial filial;
    private Integer parcela;

    public CreditoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CreditoBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public CreditoBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public CreditoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public CreditoBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public CreditoBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public CreditoBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public CreditoBuilder comNota(Nota nota) {
        this.nota = nota;
        return this;
    }

    public CreditoBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public CreditoBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public CreditoBuilder comEntrada(Boolean entrada) {
        this.entrada = entrada;
        return this;
    }

    public CreditoBuilder comSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
        return this;
    }

    public CreditoBuilder comFilial(Filial filial) {
        this.filial = filial;
        return this;
    }

    public CreditoBuilder comParcela(Integer parcela) {
        this.parcela = parcela;
        return this;
    }

    public Credito construir() throws DadoInvalidoException {
        return new Credito(id, emissao, pessoa, cotacao, historico, baixas, operacaoFinanceira, valor, vencimento, nota, entrada, situacaoDeCobranca, filial, parcela);
    }

}
