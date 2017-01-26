package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_COTACAO",
        sequenceName = "SEQ_COTACAO")
public class Cotacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_COTACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(optional = false)
    private Moeda moeda;
    @NotNull(message = "{valor_not_null}")
    private BigDecimal valor;
    @NotNull(message = "{data_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @NotNull(message = "{conta_not_null}")
    @ManyToOne(optional = false)
    private Conta conta;

    public Cotacao() {
    }

    public Cotacao(Long id, Moeda moeda, BigDecimal valor, Date data, Conta conta) throws DadoInvalidoException {
        this.id = id;
        this.moeda = moeda;
        this.valor = valor;
        this.emissao = data;
        this.conta = conta;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return NumberFormat.getCurrencyInstance(moeda.getBandeira().getLocal()).format(valor);
    }

    public Conta getConta() {
        return conta;
    }

    public Date getEmissao() {
        return emissao;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "conta");
        new ValidadorDeCampos<Cotacao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cotacao)) {
            return false;
        }
        Cotacao outro = (Cotacao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cotacao{" + "id=" + id + ", moeda=" + moeda + ", valor=" + valor + ", emissao=" + emissao + ", conta=" + conta + '}';
    }

}
