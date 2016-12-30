package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_TAXADEADMINISTRACAO",
        sequenceName = "SEQ_TAXADEADMINISTRACAO")
public class TaxaDeAdministracao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_TAXADEADMINISTRACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{numero_dias_not_null}")
    private Integer numeroDias;
    @NotNull(message = "{taxa_not_null}")
    private BigDecimal taxa;
    @ManyToOne
    private Cartao cartao;

    public TaxaDeAdministracao() {
    }

    public TaxaDeAdministracao(Long id, Integer numeroDias, BigDecimal taxa, Cartao cartao) throws DadoInvalidoException {
        this.id = id;
        this.numeroDias = numeroDias;
        this.taxa = taxa;
        this.cartao = cartao;
        ehValido();
    }
    
    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("numeroDias", "taxa");
        new ValidadorDeCampos<TaxaDeAdministracao>().valida(this, campos);
    }

    public void preparaInclusao(Cartao cartao) {
        if (this.cartao == null) {
            this.id = null;
            this.cartao = cartao;
        }
    }

    public Long getId() {
        return id;
    }

    public Integer getNumeroDias() {
        return numeroDias;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public Cartao getCartao() {
        return cartao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof TaxaDeAdministracao)) {
            return false;
        }
        TaxaDeAdministracao outro = (TaxaDeAdministracao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "TaxaDeAdministracao{" + "id=" + id + ", numeroParcelas=" + numeroDias + ", taxa=" + taxa + ", cartao=" + (cartao == null ? null : cartao.getId()) + '}';
    }

}
