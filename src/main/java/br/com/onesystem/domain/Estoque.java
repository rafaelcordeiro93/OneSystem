package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

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
    @NotNull(message = "{saldo_not_null}")
    @Min(value = 0, message = "{saldo_min}")
    @Column(nullable = false)
    private BigDecimal quantidade;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_fisica_not_null}")
    private OperacaoFisica operacaoFisica;
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();
    @ManyToOne
    private ItemEmitido itemEmitido;
    @OneToOne
    private AjusteDeEstoque ajusteDeEstoque;

    public Estoque() {
    }

    public Estoque(Long id, Item item, BigDecimal saldo, Deposito deposito,
            Date emissao, OperacaoFisica tipo, ItemEmitido itemEmitido, AjusteDeEstoque ajusteDeEstoque) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.quantidade = saldo;
        this.deposito = deposito;
        this.operacaoFisica = tipo;
        this.emissao = emissao;
        this.itemEmitido = itemEmitido;
        this.ajusteDeEstoque = ajusteDeEstoque;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "quantidade", "deposito", "operacaoFisica", "emissao");
        new ValidadorDeCampos<Estoque>().valida(this, campos);
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

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void preparaInclusaoDe(ItemEmitido itemEmitido) {
        if (this.itemEmitido == null) {
            this.id = null;
            this.itemEmitido = itemEmitido;
        }
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
        return "Estoque{" + "id=" + id + ", item=" + item + ", deposito=" + deposito + ", saldo=" + quantidade + ", operacaoFisica=" + operacaoFisica + ", emissao=" + emissao + '}';
    }
}
