/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PERFILDEVALOR_CLASSE_NOME")
public abstract class Transacao implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_PERFILDEVALOR", sequenceName = "SEQ_PERFILDEVALOR",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_PERFILDEVALOR", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    protected BigDecimal valor;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @ManyToOne(optional = true)
    private Pessoa pessoa;

    @NotNull(message = "cotacao_not_null")
    @ManyToOne(optional = false)
    private Cotacao cotacao;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    private String historico;

    @OneToMany(mappedBy = "transacao", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira operacaoFinanceira;

    @NotNull(message = "{vencimento_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    public Transacao() {
    }

    public Transacao(Long id, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico,
            List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira, BigDecimal valor, Date vencimento) throws DadoInvalidoException {
        this.id = id;
        this.valor = valor;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.cotacao = cotacao;
        this.historico = historico;
        this.operacaoFinanceira = operacaoFinanceira;
        this.baixas = baixas;
        this.vencimento = vencimento;
        ehAbstracaoValida();
    }

    private final void ehAbstracaoValida() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "vencimento", "historico", "cotacao", "operacaoFinanceira");
        new ValidadorDeCampos<Transacao>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public String getHistorico() {
        return historico;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
    }

    public String getVencimentoFormatadoSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public String getValorFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getValor());
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Transacao)) {
            return false;
        }
        Transacao outro = (Transacao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
