/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDePedido;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
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

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PEDIDO_CLASSE_NOME")
public abstract class Pedido implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_PEDIDO", sequenceName = "SEQ_PEDIDO",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_PEDIDO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne
    private Pessoa pessoa;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @ManyToOne
    private FormaDeRecebimento formaDeRecebimento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date previsaoDeEntrega;
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @Column(nullable = true)
    private String contato;
    @Column(nullable = true)
    private String observacao;
    @Enumerated(EnumType.STRING)
    private EstadoDePedido estado;
    @NotNull(message = "{moeda_padrao_not_null}")
    @ManyToOne(optional = false)
    private Moeda moeda;
    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    protected List<ItemDePedido> itens;
    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    protected List<ParcelaDePedido> parcelaDePedido;
    @Min(value = 0, message = "{valorDesconto_min}")
    @Column(nullable = true)
    private BigDecimal desconto;
    @Min(value = 0, message = "{valor_acrescimo_min}")
    @Column(nullable = true)
    private BigDecimal acrescimo = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_despesa_cobranca_min}")
    private BigDecimal despesaCobranca = BigDecimal.ZERO;
    @Min(value = 0, message = "{valor_frete_min}")
    private BigDecimal frete = BigDecimal.ZERO;
    @Min(value = 0, message = "{min_dinheiro}")
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;

    public Pedido() {
        emissao = new Date(); // Necesário para construção do estoque.
    }

    public Pedido(Long id, Pessoa pessoa, Operacao operacao, List<ItemDePedido> itens, String contato, EstadoDePedido estado,
            FormaDeRecebimento formaDeRecebimento, List<ParcelaDePedido> parcelaDePedido, Moeda moeda, BigDecimal desconto,
            BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete,
            BigDecimal totalEmDinheiro, Date emissao, Date previsaoDeEntrega, String observacao) throws DadoInvalidoException {

        this.id = id;
        this.emissao = emissao;
        this.previsaoDeEntrega = previsaoDeEntrega;
        this.pessoa = pessoa;
        this.contato = contato;
        this.operacao = operacao;
        this.formaDeRecebimento = formaDeRecebimento;
        this.parcelaDePedido = parcelaDePedido;
        this.moeda = getMoedaPadrao();
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.despesaCobranca = despesaCobranca;
        this.frete = frete;
        this.totalEmDinheiro = totalEmDinheiro;
        this.itens = itens;
        this.observacao = observacao;
        if (estado == null) {
            this.estado = EstadoDePedido.EM_DEFINICAO;
        } else {
            this.estado = estado;
        }
        ehValido();
    }

    public void cancela() {
        estado = EstadoDePedido.CANCELADO;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("pessoa", "operacao", "moedaPadrao");
        new ValidadorDeCampos<>().valida(this, campos);
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

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public Date getPrevisaoDeEntrega() {
        return previsaoDeEntrega;
    }

    public Date getEmissao() {
        return emissao;
    }

    public String getContato() {
        return contato;
    }

    public EstadoDePedido getEstado() {
        return estado;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public List<ItemDePedido> getItens() {
        return itens;
    }

    public List<ParcelaDePedido> getParcelaDePedido() {
        return parcelaDePedido;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getTotalItensFormatado() {
        return MoedaFormatter.format(moeda, getTotalItens());
    }

    public String getTotalParcelasFormatado() {
        return MoedaFormatter.format(moeda, getTotalPedido());
    }

    public String getAcrescimoFormatado() {
        return MoedaFormatter.format(moeda, getAcrescimo());
    }

    public String getTotalEmDinheiroFormatado() {
        return MoedaFormatter.format(moeda, getTotalEmDinheiro());
    }

    public String getDescontoFormatado() {
        return MoedaFormatter.format(moeda, getDesconto());
    }

    public String getDespesaCobrancaFormatado() {
        return MoedaFormatter.format(moeda, getDespesaCobranca());
    }

    public String getFreteFormatado() {
        return MoedaFormatter.format(moeda, getFrete());
    }

    public BigDecimal getTotalParcelas() {
        if (parcelaDePedido != null) {
            return parcelaDePedido.stream().filter(c -> c.getValor() != null).map(ParcelaDePedido::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public String getTotalPedidoFormatado() {
        return MoedaFormatter.format(moeda, getTotalPedido());
    }

    public BigDecimal getTotalItens() {
        return itens.stream().map(ItemDePedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPedido() {
        BigDecimal a = acrescimo == null ? BigDecimal.ZERO : acrescimo;
        BigDecimal f = frete == null ? BigDecimal.ZERO : frete;
        BigDecimal c = despesaCobranca == null ? BigDecimal.ZERO : despesaCobranca;
        BigDecimal d = desconto == null ? BigDecimal.ZERO : desconto;
        return getTotalItens().add(a.add(f.add(c))).subtract(d);
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

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public Moeda getMoedaPadrao() throws EDadoInvalidoException {
        Configuracao cfg = new ConfiguracaoService().buscar();
        return cfg.getMoedaPadrao();
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Pedido)) {
            return false;
        }
        Pedido outro = (Pedido) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
