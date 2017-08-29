/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import br.com.onesystem.valueobjects.EstadoDeNota;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_CONDICIONAL",
        name = "SEQ_CONDICIONAL")
public class Condicional implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONDICIONAL", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @ManyToOne
    private ListaDePreco listaDePreco;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne
    private Cotacao cotacao;
    @OneToMany(mappedBy = "condicional", cascade = {CascadeType.ALL})
    private List<ItemDeCondicional> itensDeCondicional;
    @Length(max = 1000, min = 0, message = "{observacao_lenght}")
    @Column(length = 1000)
    private String observacao;
    @Enumerated(EnumType.STRING)
    private EstadoDeCondicional estado;
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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "condicional")
    private List<NotaEmitida> notasEmitidas;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @NotNull(message = "{filial_not_null}")
    @ManyToOne(optional = false)
    private Filial filial;

    public Condicional() {
        this.estado = EstadoDeCondicional.EM_DEFINICAO;
    }

    public Condicional(Long id) {
        this.id = id;
    }

    public Condicional(Long id, Pessoa pessoa, ListaDePreco listaDePreco, Cotacao cotacao, List<ItemDeCondicional> itensDeCondicional, Operacao operacao,
            String observacao, BigDecimal desconto, BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete, Filial filial) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.listaDePreco = listaDePreco;
        this.emissao = new Date();
        this.operacao = operacao;
        this.cotacao = cotacao;
        this.observacao = observacao;
        this.estado = EstadoDeCondicional.EM_DEFINICAO;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.filial = filial;
        gera(itensDeCondicional);
        ehValido();
    }

    private void gera(List<ItemDeCondicional> itensDeCondicional) throws DadoInvalidoException {
        if (itensDeCondicional != null && !itensDeCondicional.isEmpty()) {
            for (ItemDeCondicional i : itensDeCondicional) {
                i.paraCondicional(this);
            }
            this.itensDeCondicional = itensDeCondicional;
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Itens_not_null"));
        }
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "observacao", "cotacao", "desconto", "acrescimo", "despesaCobranca", "frete", "operacao", "filial");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public void cancela() {
        this.estado = EstadoDeCondicional.CANCELADO;
    }

    public void efetiva() {
        this.estado = EstadoDeCondicional.EFETIVADO;
    }

    public String getAcrescimoFormatado() {
        if (cotacao != null) {
            return MoedaFormatter.format(cotacao.getConta().getMoeda(), getAcrescimo());
        } else {
            return MoedaFormatter.format(getAcrescimo());
        }
    }

    public String getDescontoFormatado() {
        if (cotacao != null) {
            return MoedaFormatter.format(cotacao.getConta().getMoeda(), getDesconto());
        } else {
            return MoedaFormatter.format(getDesconto());
        }
    }

    public String getDespesaCobrancaFormatado() {
        if (cotacao != null) {
            return MoedaFormatter.format(cotacao.getConta().getMoeda(), getDespesaCobranca());
        } else {
            return MoedaFormatter.format(getDespesaCobranca());
        }
    }

    public Filial getFilial() {
        return filial;
    }

    public String getFreteFormatado() {
        if (cotacao != null) {
            return MoedaFormatter.format(cotacao.getConta().getMoeda(), getFrete());
        } else {
            return MoedaFormatter.format(getFrete());
        }
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

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public Date getEmissao() {
        return emissao;
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public List<NotaEmitida> getNotasEmitidas() {
        return notasEmitidas;
    }

    public List<ItemDeCondicional> getItensDeCondicional() {
        itensDeCondicional.sort(Comparator.comparing(ItemDeCondicional::getId));
        return itensDeCondicional;
    }

    public BigDecimal getTotalItens() {
        return itensDeCondicional.stream().map(ItemDeCondicional::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getTotalItensFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotalItens());
    }

    public String getTotalFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), getTotal());
    }

    public BigDecimal getTotal() {
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
        BigDecimal total = this.notasEmitidas.stream().filter(n -> !n.getEstado().equals(EstadoDeNota.CANCELADO)).map(n -> n.getItens().stream().map(ItemDeNota::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add)).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.equals(getItensDeCondicional().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add))) {
            efetiva();
        }
    }

    public EstadoDeCondicional getEstado() {
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
        if (!(objeto instanceof Condicional)) {
            return false;
        }
        Condicional outro = (Condicional) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Condicional{" + "id=" + id + ", listaDePreco=" + listaDePreco + ", emissao=" + emissao + ", cotacao=" + cotacao + ", observacao=" + observacao + ", estado=" + estado + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", despesaCobranca=" + despesaCobranca + ", frete=" + frete + '}';
    }

}
