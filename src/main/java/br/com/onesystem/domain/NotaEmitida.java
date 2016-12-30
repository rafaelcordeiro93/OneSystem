/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ItemEmitido> itensEmitidos;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Titulo> titulos;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @NotNull(message = "{valor_desconto_not_null}")
    @Max(value = 9999999, message = "{valor_desconto_max}")
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = false)
    private BigDecimal desconto;
    @NotNull(message = "{valor_acrescimo_not_null}")
    @Max(value = 9999999, message = "{valor_acrescimo_max}")
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = false)
    private BigDecimal acrescimo;
    @Min(value = 0, message = "{valor_frete}")
    private BigDecimal frete;
    @Min(value = 0, message = "{valor_despesasCobranca}")
    private BigDecimal despesasCobranca;
    @OneToOne(mappedBy = "notaEmitida", cascade = {CascadeType.ALL})
    private FormaDeRecebimentoOuPagamento formaDeRecebimentoOuPagamento;
    @OneToMany(mappedBy = "notaEmitida", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Baixa> baixas;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    private boolean cancelada;

    public NotaEmitida() {
    }

    public NotaEmitida(Long id, Pessoa pessoa, Operacao operacao, List<ItemEmitido> itensEmitidos, List<Titulo> titulos, ListaDePreco listaDePreco, BigDecimal desconto, BigDecimal acrescimo, BigDecimal frete, BigDecimal despesasCobranca, FormaDeRecebimentoOuPagamento formaDeRecebimentoOuPagamento, List<Baixa> baixas, Date emissao, boolean cancelada) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.itensEmitidos = itensEmitidos;
        this.titulos = titulos;
        this.listaDePreco = listaDePreco;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.frete = frete;
        this.despesasCobranca = despesasCobranca;
        this.formaDeRecebimentoOuPagamento = formaDeRecebimentoOuPagamento;
        this.baixas = baixas;
        this.emissao = emissao;
        this.cancelada = cancelada;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "operacao", "desconto",
                "acrescimo", "frete", "despesasCobranca");
        new ValidadorDeCampos<NotaEmitida>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public BigDecimal getDespesasCobranca() {
        return despesasCobranca;
    }

    public FormaDeRecebimentoOuPagamento getFormaDeRecebimentoOuPagamento() {
        return formaDeRecebimentoOuPagamento;
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public Date getEmissao() {
        return emissao;
    }

    public boolean isCancelada() {
        return cancelada;
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
        return "NotaEmitida{" + "id=" + id + ", pessoa=" + pessoa + ", operacao=" + operacao + ", itensEmitidos=" + itensEmitidos + ", titulos=" + titulos + ", listaDePreco=" + listaDePreco + ", desconto=" + desconto + ", acrescimo=" + acrescimo + '}';
    }

}
