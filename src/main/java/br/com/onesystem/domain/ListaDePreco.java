package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.CaseType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_LISTAPRECO",
        sequenceName = "SEQ_LISTAPRECO")
public class ListaDePreco implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_LISTAPRECO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 80)
    @NotNull(message = "{nome_not_null}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{nome_type_letter_space}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    private String nome;
    @OneToMany(mappedBy = "listaDePreco")
    private List<PrecoDeItem> precos;
    @OneToMany(mappedBy = "listaDePreco")
    private List<Nota> notas;
    @OneToMany(mappedBy = "listaDePreco")
    private List<Orcamento> orcamentos;

    public ListaDePreco() {
    }

    public ListaDePreco(Long id, String nome) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<ListaDePreco>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ListaDePreco)) {
            return false;
        }
        ListaDePreco outro = (ListaDePreco) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
