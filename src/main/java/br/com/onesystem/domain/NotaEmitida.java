/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_NOTAEMITIDA",
        sequenceName = "SEQ_NOTAEMITIDA")
public class NotaEmitida implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_NOTAEMITIDA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ItemEmitido> itensEmitidos;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Titulo> titulos;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @NotNull(message = "{valor_desconto_not_null}")
    @Max(value = 9999999, message = "{valor_desconto_max}")
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;
    @NotNull(message = "{valor_acrescimo_not_null}")
    @Max(value = 9999999, message = "{valor_acrescimo_max}")
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = false)
    private BigDecimal acrescimo = BigDecimal.ZERO;

    public NotaEmitida(Long id, Pessoa pessoa, Operacao operacao, List<ItemEmitido> itensEmitidos,
            List<Titulo> titulos, ListaDePreco listaDePreco, BigDecimal desconto,
            BigDecimal acrescimo) {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.itensEmitidos = itensEmitidos;
        this.titulos = titulos;
        this.listaDePreco = listaDePreco;        
        this.acrescimo = acrescimo;
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }

    public void setItensEmitidos(List<ItemEmitido> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof NotaEmitida)) {
            return false;
        }
        NotaEmitida outro = (NotaEmitida) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "NotaEmitida{" + "id=" + id + ", pessoa=" + pessoa + ", operacao=" + operacao + '}';
    }

}
