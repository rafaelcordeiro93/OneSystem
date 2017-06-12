package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
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
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_FILIAL",
        sequenceName = "SEQ_FILIAL")
public class Filial implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_FILIAL", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 2, max = 60, message = "{nome_lenght}")
    @Column(length = 60, nullable = false)
    private String nome;
    @NotNull(message = "{razao_social_not_null}")
    @Length(min = 2, max = 60, message = "{razao_social_lenght}")
    @Column(length = 60, nullable = false)
    private String razao_social;
    @NotNull(message = "{fantasia_not_null}")
    @Length(min = 2, max = 60, message = "{fantasia_lenght}")
    @Column(length = 60, nullable = false)
    private String fantasia;
    @NotNull(message = "{ruc_not_null}")
    @Length(min = 2, max = 60, message = "{ruc_lenght}")
    @Column(length = 60, nullable = false)
    private String ruc;
    @NotNull(message = "{endereco_not_null}")
    @Length(min = 2, max = 60, message = "{endereco_lenght}")
    @Column(length = 60, nullable = false)
    private String endereco;
    @NotNull(message = "{bairro_not_null}")
    @Length(min = 2, max = 60, message = "{bairro_lenght}")
    @Column(length = 60, nullable = false)
    private String bairro;
    @NotNull(message = "{cidade_not_null}")
    @ManyToOne
    private Cidade cidade;
    @NotNull(message = "{telefone_not_null}")
    @Length(min = 2, max = 60, message = "{telefone_lenght}")
    @Column(length = 60, nullable = false)
    private String telefone;

    public Filial() {
    }

    public Filial(Long id, String nome, String razao_social, String fantasia,
            String ruc, String endereco, String bairro, Cidade cidade,
            String telefone) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.razao_social = razao_social;
        this.fantasia = fantasia;
        this.ruc = ruc;
        this.cidade = cidade;
        this.endereco = endereco;
        this.bairro = bairro;
        this.telefone = telefone;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public String getFantasia() {
        return fantasia;
    }

    public String getRuc() {
        return ruc;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Filial>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Filial)) {
            return false;
        }
        Filial outro = (Filial) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Filial{" + "id=" + id + ", nome=" + nome + ", razao_social=" + razao_social + ", fantasia=" + fantasia + ", ruc=" + ruc + ", endereco=" + endereco + ", bairro=" + bairro + ", cidade=" + cidade + ", telefone=" + telefone + '}';
    }

}
