package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class BoletoDeCartaoBuilder {

    private Long id;
    private Nota nota;
    private Cartao cartao;
    private String historico;
    private Date emissao;
    private Date vencimento;
    private BigDecimal valor;
    private String codigoTransacao;
    private SituacaoDeCartao tipoSituacao;
    private Cotacao cotacao;
    private Pessoa pessoa;
    private List<Baixa> baixas;
    private OperacaoFinanceira operacaoFinanceira;
    private Boolean entrada;

    public BoletoDeCartaoBuilder() {
    }

    public BoletoDeCartaoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public BoletoDeCartaoBuilder comNota(Nota nota) {
        this.nota = nota;
        return this;
    }

    public BoletoDeCartaoBuilder comCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public BoletoDeCartaoBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public BoletoDeCartaoBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public BoletoDeCartaoBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public BoletoDeCartaoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public BoletoDeCartaoBuilder comCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
        return this;
    }

    public BoletoDeCartaoBuilder comTipoSituacao(SituacaoDeCartao tipoSituacao) {
        this.tipoSituacao = tipoSituacao;
        return this;
    }

    public BoletoDeCartaoBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public BoletoDeCartaoBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public BoletoDeCartaoBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public BoletoDeCartaoBuilder comEntrada(Boolean entrada) {
        this.entrada = entrada;
        return this;
    }

    public BoletoDeCartaoBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public BoletoDeCartao construir() throws DadoInvalidoException {
        return new BoletoDeCartao(id, nota, cartao, emissao, valor, codigoTransacao, tipoSituacao, historico, vencimento, cotacao, pessoa, baixas, operacaoFinanceira, entrada);
    }

}
