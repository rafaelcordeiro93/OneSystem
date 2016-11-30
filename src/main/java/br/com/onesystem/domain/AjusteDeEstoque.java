package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.OperacaoFisica;
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
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_AJUSTEDEESTOQUE",
        sequenceName = "SEQ_AJUSTEDEESTOQUE")
public class AjusteDeEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_AJUSTEDEESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{observacao_not_null}")
    @Length(min = 2, max = 60, message = "{observacao_lenght}")
    @Column(length = 60, nullable = false)
    private String observacao;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{quantidade_not_null}")
    @Min(value = 0, message = "{quantidade_min}")
    @Column(nullable = false)
    private BigDecimal quantidade;
    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;
    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = new Date();
    @NotNull(message = "{tipoOperacao_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFisica tipo;

    public AjusteDeEstoque() {
    }

    public AjusteDeEstoque(Long id, String observacao, Item item, BigDecimal quantidade, Deposito deposito, Date emissao, OperacaoFisica tipo) throws DadoInvalidoException {
        this.id = id;
        this.observacao = observacao;
        this.item = item;
        this.quantidade = quantidade;
        this.deposito = deposito;
        this.emissao = emissao;
        this.tipo = tipo;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "quantidade", "deposito", "observacao");
        new ValidadorDeCampos<AjusteDeEstoque>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getObservacao() {
        return observacao;
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

    public OperacaoFisica getTipo() {
        return tipo;
    }

    public String getDataFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return emissaoFormatada.format(getEmissao().getTime());
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof AjusteDeEstoque)) {
            return false;
        }
        AjusteDeEstoque outro = (AjusteDeEstoque) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "AjusteDeEstoque{" + "id=" + id + ", observacao=" + observacao + ", item=" + item + ", quantidade=" + quantidade + ", deposito=" + deposito + ", emissao=" + emissao + '}';
    }
}
