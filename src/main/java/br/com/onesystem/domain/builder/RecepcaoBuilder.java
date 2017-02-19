package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class RecepcaoBuilder {

    private Long id;
    private Pessoa pessoa;
    private BigDecimal valor;
    private Date emissao = Calendar.getInstance().getTime();
    private Cotacao cotacao;
    private Titulo titulo;

    public RecepcaoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public RecepcaoBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public RecepcaoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public RecepcaoBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public RecepcaoBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public RecepcaoBuilder comTitulo(Titulo titulo) {
        this.titulo = titulo;
        return this;
    }

    public Recepcao construir() throws DadoInvalidoException {
        return new Recepcao(id, pessoa, valor, emissao, titulo, cotacao);
    }

}
