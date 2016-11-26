package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CIDADE",
        sequenceName = "SEQ_CIDADE")
public class Cidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CIDADE")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 60, message = "{nome_lenght}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{nome_type_letter_space}")
    @Column(nullable = false, length = 60)
    private String nome;
    @NotNull(message = "{uf_not_null}")
    @Length(max = 2, min = 2, message = "{uf_length}")
    @CharacterType(value = CaseType.LETTER, message = "{uf_type_letter}")
    @Column(nullable = false, length = 2)
    private String uf;
    @NotNull(message = "{pais_not_null}")
    @Length(max = 80, min = 3, message = "{pais_lenght}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{pais_type_letter_space}")
    @Column(nullable = false, length = 80)
    private String pais;
    @OneToMany(mappedBy = "cidade", fetch = FetchType.LAZY)
    private List<Pessoa> listadePessoas;

    public Cidade() {
    }

    public Cidade(Long id, String nome, String uf, String pais) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.pais = pais;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "uf", "pais");
        new ValidadorDeCampos<Cidade>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cidade)) {
            return false;
        }
        Cidade outro = (Cidade) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cidade{" + "id=" + id + ", nome=" + nome + ", uf=" + uf + ", pais=" + pais + '}';
    }

}
