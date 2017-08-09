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

import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_TABELADETRIBUTACAO",
        sequenceName = "SEQ_TABELADETRIBUTACAO")
public class TabelaDeTributacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_TABELADETRIBUTACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{IVA_not_null}")
    @Max(value = 100, message = "{IVA_max}")
    @Min(value = 0, message = "{IVA_min}")
    @Column(nullable = false, unique = true)
    private BigDecimal iva;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, min = 4, message = "{nome_length}")
    @Column(nullable = false, length = 60)
    private String nome;
    @OneToMany(mappedBy = "tabelaDeTributacao")
    private List<SituacaoFiscal> situacoesFiscais;

    public TabelaDeTributacao() {
    }

    public TabelaDeTributacao(Long id, BigDecimal iva, String nome) throws DadoInvalidoException {
        this.id = id;
        this.iva = iva;
        this.nome = nome;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getIva() {
        if (iva == null) {
            return BigDecimal.ZERO;
        }
        return iva;
    }

    public String getNome() {
        return nome;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("iva", "nome");
        new ValidadorDeCampos<TabelaDeTributacao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof TabelaDeTributacao)) {
            return false;
        }
        TabelaDeTributacao outro = (TabelaDeTributacao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "TabelaDeTributacao{" + "ID=" + id + ", iva=" + iva + ", nome=" + nome + '}';
    }

}
