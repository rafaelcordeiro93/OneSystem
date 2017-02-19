/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
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
import javax.validation.constraints.Max;
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
public abstract class PerfilDeValor implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_PERFILDEVALOR", sequenceName = "SEQ_PERFILDEVALOR",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_PERFILDEVALOR", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    protected BigDecimal valor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

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

    @OneToMany(mappedBy = "perfilDeValor", cascade = {CascadeType.ALL})
    private List<Baixa> baixa;
    
    @ManyToOne
    private Movimento movimento;

    public PerfilDeValor() {
    }

    public PerfilDeValor(Long id, BigDecimal valor, Date vencimento, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico, List<Baixa> baixa) {
        this.id = id;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.cotacao = cotacao;
        this.historico = historico;
        this.baixa = baixa;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getVencimento() {
        return vencimento;
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

    public List<Baixa> getBaixas() {
        return baixa;
    }

    public Movimento getMovimento() {
        return movimento;
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

    public String getVencimentoFormatadoSemHoras() {
        SimpleDateFormat vencimentoFormadado = new SimpleDateFormat("dd/MM/yyyy");
        return getVencimento() != null ? vencimentoFormadado.format(getVencimento().getTime()) : "";
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof PerfilDeValor)) {
            return false;
        }
        PerfilDeValor outro = (PerfilDeValor) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
