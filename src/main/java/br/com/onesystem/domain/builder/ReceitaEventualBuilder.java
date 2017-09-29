package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaEventual;
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
public class ReceitaEventualBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private Cotacao cotacao;
    private TipoReceita receita;
    private OperacaoFinanceira operacaoFinanceira;
    private Date referencia;
    private SituacaoDeCobranca situacaoDeCobranca;
    private Filial filial;

    public ReceitaEventualBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ReceitaEventualBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public ReceitaEventualBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public ReceitaEventualBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ReceitaEventualBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public ReceitaEventualBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public ReceitaEventualBuilder comReceita(TipoReceita receita) {
        this.receita = receita;
        return this;
    }

    public ReceitaEventualBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ReceitaEventualBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public ReceitaEventualBuilder comReferencia(Date referencia) {
        this.referencia = referencia;
        return this;
    }

    public ReceitaEventualBuilder comSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
        return this;
    }

    public ReceitaEventualBuilder comFilial(Filial filial){
        this.filial = filial;
        return this;
    }
    
    public ReceitaEventual construir() throws DadoInvalidoException {
        return new ReceitaEventual(id, pessoa, receita, valor, emissao, historico, cotacao, operacaoFinanceira, referencia, situacaoDeCobranca, filial);
    }

}
