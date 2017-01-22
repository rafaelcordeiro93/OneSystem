/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoContabil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CREDITO",
        sequenceName = "SEQ_CREDITO")
public class Credito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CREDITO")
    private Long id;
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = false)
    private BigDecimal valor;
    @OneToOne
    private NotaEmitida notaEmitida;
    @Enumerated(EnumType.STRING)
    private TipoContabil tipoContabil;

    public Credito() {
    }

    public Credito(Long id, Date emissao, Pessoa pessoa, BigDecimal valor, NotaEmitida notaEmitida, TipoContabil tipoContabil) throws DadoInvalidoException {
        this.id = id;
        this.emissao = emissao;
        this.pessoa = pessoa;
        this.valor = valor;
        this.notaEmitida = notaEmitida;
        this.tipoContabil = tipoContabil;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("emissao","valor");
        new ValidadorDeCampos<Credito>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public TipoContabil getTipoContabil() {
        return tipoContabil;
    }
}
