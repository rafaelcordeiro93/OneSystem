/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFomatter;
import br.com.onesystem.valueobjects.EstadoDeComanda;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_COMANDA",
        name = "SEQ_COMANDA")
public class Comanda implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_COMANDA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne
    private Cotacao cotacao;
    @OneToMany(mappedBy = "comanda", cascade = {CascadeType.ALL})
    private List<ItemDeComanda> itensDeComanda;
    @Length(max = 1000, min = 0, message = "{observacao_lenght}")
    @Column(length = 1000)
    private String observacao;
    @Enumerated(EnumType.STRING)
    private EstadoDeComanda estado;
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo;
    @Min(value = 0, message = "{valor_despesa_cobranca_min}")
    private BigDecimal despesaCobranca;
    @Min(value = 0, message = "{valor_frete_min}")
    private BigDecimal frete;
    @Min(value = 0, message = "{numero_comanda_min}")
    @Max(value = 999999, message = "{numero_comanda_max}")
    private Integer numeroComanda;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "comanda")
    private List<NotaEmitida> notasEmitidas;

    public Comanda() {
        this.estado = EstadoDeComanda.EM_DEFINICAO;
    }

    public Comanda(Long id) {
        this.id = id;
    }

    public Comanda(Long id, ListaDePreco listaDePreco, Cotacao cotacao, List<ItemDeComanda> itensDeComanda,
            String observacao, BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete, Integer numeroComanda) throws DadoInvalidoException {
        this.id = id;
        this.listaDePreco = listaDePreco;
        this.emissao = new Date();
        this.cotacao = cotacao;
        this.itensDeComanda = itensDeComanda;
        this.observacao = observacao;
        this.estado = EstadoDeComanda.EM_DEFINICAO;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.numeroComanda = numeroComanda;
        gera(itensDeComanda);
        ehValido();
    }

    private void gera(List<ItemDeComanda> itensDeComanda) throws DadoInvalidoException {
        for (ItemDeComanda i : itensDeComanda) {
            i.paraComanda(this);
        }
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("observacao", "cotacao", "desconto", "acrescimo", "despesaCobranca", "frete", "numeroComanda");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void cancela() {
        this.estado = EstadoDeComanda.CANCELADO;
    }

    public void efetiva() {
        this.estado = EstadoDeComanda.EFETIVADO;
    }

    public String getAcrescimoFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getAcrescimo());
        } else {
            return MoedaFomatter.format(getAcrescimo());
        }
    }

    public String getDescontoFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getDesconto());
        } else {
            return MoedaFomatter.format(getDesconto());
        }
    }

    public String getDespesaCobrancaFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getDespesaCobranca());
        } else {
            return MoedaFomatter.format(getDespesaCobranca());
        }
    }

    public String getFreteFormatado() {
        if (cotacao != null) {
            return MoedaFomatter.format(cotacao.getConta().getMoeda(), getFrete());
        } else {
            return MoedaFomatter.format(getFrete());
        }
    }

    public Integer getNumeroComanda() {
        return numeroComanda;
    }

    public Long getId() {
        return id;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public List<NotaEmitida> getNotasEmitidas() {
        return notasEmitidas;
    }

    public List<ItemDeComanda> getItensDeComanda() {
        itensDeComanda.sort(Comparator.comparing(ItemDeComanda::getId));
        return itensDeComanda;
    }

    public BigDecimal getTotalItens() {
        return itensDeComanda.stream().map(ItemDeComanda::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalItensFormatado() {
        return MoedaFomatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    public String getTotalComandaFormatado() {
        return MoedaFomatter.format(cotacao.getConta().getMoeda(), getTotalComanda());
    }

    public BigDecimal getTotalComanda() {
        BigDecimal a = getAcrescimo() == null ? BigDecimal.ZERO : getAcrescimo();
        BigDecimal f = getFrete() == null ? BigDecimal.ZERO : getFrete();
        BigDecimal c = getDespesaCobranca() == null ? BigDecimal.ZERO : getDespesaCobranca();
        BigDecimal d = getDesconto() == null ? BigDecimal.ZERO : getDesconto();
        return getTotalItens().add(a.add(f.add(c))).subtract(d);
    }

    public String getObservacao() {
        return observacao;
    }

    public void adiciona(NotaEmitida nota) {
        this.notasEmitidas.add(nota);
    }

    public EstadoDeComanda getEstado() {
        return estado;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Comanda)) {
            return false;
        }
        Comanda outro = (Comanda) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Comanda{" + "id=" + id + ", listaDePreco=" + listaDePreco + ", emissao=" + emissao + ", cotacao=" + cotacao + ", observacao=" + observacao + ", estado=" + estado + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", despesaCobranca=" + despesaCobranca + ", frete=" + frete + '}';
    }

}
