package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaEventual;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class DespesaEventualBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private Cotacao cotacao;
    private TipoDespesa despesa;
    private OperacaoFinanceira operacaoFinanceira;
    private Date referencia;
    private SituacaoDeCobranca situacaoDeCobranca;
    private Filial filial;

    public DespesaEventualBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public DespesaEventualBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public DespesaEventualBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public DespesaEventualBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public DespesaEventualBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public DespesaEventualBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public DespesaEventualBuilder comDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
        return this;
    }

    public DespesaEventualBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public DespesaEventualBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public DespesaEventualBuilder comReferencia(Date referencia) {
        this.referencia = referencia;
        return this;
    }
    
    public DespesaEventualBuilder comSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca){
        this.situacaoDeCobranca = situacaoDeCobranca;
        return this;
    }

    public DespesaEventualBuilder comFilial(Filial filial){
        this.filial = filial;
        return this;
    }
    
    public DespesaEventual construir() throws DadoInvalidoException {
        return new DespesaEventual(id, pessoa, despesa, valor, emissao, historico, cotacao, operacaoFinanceira, referencia, situacaoDeCobranca, filial);
    }

}
