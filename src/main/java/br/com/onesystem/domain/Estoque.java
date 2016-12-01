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
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;
    @NotNull(message = "{saldo_not_null}")
    @Min(value = 0, message = "{saldo_min}")
    @Column(nullable = false)
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{tipoOperacao_not_null}")
    private OperacaoFisica tipo;
     @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();   

    public Estoque() {
    }

    public Estoque(Long id, Item item, BigDecimal saldo, Deposito deposito, OperacaoFisica tipo, Date emissao) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.saldo = saldo;
        this.deposito = deposito;
        this.tipo = tipo;
        this.emissao = emissao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "saldo", "deposito");
        new ValidadorDeCampos<Estoque>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public OperacaoFisica getTipo() {
        return tipo;
    }

    public Date getEmissao() {
        return emissao;
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
        return "AjusteDeEstoque{" + "id=" + id + ", item=" + item + ", saldo=" + saldo + ", deposito=" + deposito + ", tipo=" + tipo + ", emissao=" + emissao + '}';
    }
}
