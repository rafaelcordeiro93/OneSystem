package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CARTAO",
        sequenceName = "SEQ_CARTAO")
public class Cartao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CARTAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{observacao_not_null}")
    @Length(min = 2, max = 60, message = "{observacao_lenght}")
    @Column(length = 60, nullable = false)
    private String nome;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Conta conta;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Despesa despesa;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Despesa juros;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "cartao")
    private List<TaxaDeAdministracao> taxaDeAdministracao;

    public Cartao() {
    }

    public Cartao(Long id, String nome, Conta conta, Despesa despesa, Despesa juros, List<TaxaDeAdministracao> taxaDeAdministracao) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.conta = conta;
        this.despesa = despesa;
        this.juros = juros;
        this.taxaDeAdministracao = taxaDeAdministracao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Cartao>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Conta getConta() {
        return conta;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public Despesa getJuros() {
        return juros;
    }

    public List<TaxaDeAdministracao> getTaxaDeAdministracao() {
        return taxaDeAdministracao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cartao)) {
            return false;
        }
        Cartao outro = (Cartao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cartao{" + "id=" + id + ", nome=" + nome + ", conta=" + conta + ", despesa=" + despesa + ", juros=" + juros + ", taxaDeAdministracao=" + taxaDeAdministracao + '}';
    }

}
