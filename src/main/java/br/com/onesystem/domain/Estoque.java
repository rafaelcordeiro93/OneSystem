package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFisica;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_ESTOQUE",
        sequenceName = "SEQ_ESTOQUE")
public class Estoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_ESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Item item;
    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;
    @NotNull(message = "{quantidade_not_null}")
    @Min(value = 0, message = "{quantidade_min}")
    @Column(nullable = false)
    private BigDecimal quantidade;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();
    @NotNull(message = "{operacao_de_estoque_not_null}")
    @ManyToOne(optional = false)
    private OperacaoDeEstoque operacaoDeEstoque;
    @ManyToOne
    private ItemDeNota itemDeNota;
    @ManyToOne
    private AjusteDeEstoque ajusteDeEstoque;
    @ManyToOne
    private ItemDeCondicional itemDeCondicional;

    public Estoque() {
    }

    public Estoque(Long id, Item item, BigDecimal quantidade, Deposito deposito,
            Date emissao, ItemDeNota itemDeNota, AjusteDeEstoque ajusteDeEstoque,
            OperacaoDeEstoque operacaoDeEstoque, ItemDeCondicional itemDeCondicional) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.quantidade = quantidade;
        this.deposito = deposito;
        this.operacaoDeEstoque = operacaoDeEstoque;
        this.emissao = emissao;
        this.itemDeNota = itemDeNota;
        this.ajusteDeEstoque = ajusteDeEstoque;
        this.itemDeCondicional = itemDeCondicional;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "quantidade", "deposito", "emissao", "operacaoDeEstoque");
        new ValidadorDeCampos<Estoque>().valida(this, campos);
    }

    public void atualizaQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public Date getEmissao() {
        return emissao;
    }

    public OperacaoDeEstoque getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    public ItemDeNota getItemDeNota() {
        return itemDeNota;
    }

    public AjusteDeEstoque getAjusteDeEstoque() {
        return ajusteDeEstoque;
    }

    public ItemDeCondicional getItemDeCondicional() {
        return itemDeCondicional;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Estoque)) {
            return false;
        }
        Estoque outro = (Estoque) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Estoque{" + "id=" + id + ", item=" + (item != null ? item.getId() : null)
                + ", deposito=" + (deposito != null ? deposito.getId() : null)
                + ", quantidade=" + quantidade + ", emissao=" + emissao
                + ", operacaoDeEstoque=" + (operacaoDeEstoque != null ? operacaoDeEstoque.getId() : null) + ", itemEmitido="
                + (itemDeNota != null ? itemDeNota.getId() : null)
                + ", ajusteDeEstoque=" + (ajusteDeEstoque != null ? ajusteDeEstoque.getId() : null) + '}';
    }

}
