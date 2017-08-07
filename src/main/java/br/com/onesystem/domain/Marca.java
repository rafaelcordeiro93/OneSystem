package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
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

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_MARCA",
        sequenceName = "SEQ_MARCA")
public class Marca implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_MARCA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 2, max = 60, message = "{nome_length}")
    @Column(length = 60, nullable = false)
    private String nome;
    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY)
    private List<Item> mercaderias;

    public Marca() {
    }

    public Marca(Long id, String nome) throws DadoInvalidoException {
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

//    public List<Item> getMercaderias() {
//        return mercaderias;
//    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Marca>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Marca)) {
            return false;
        }
        Marca outro = (Marca) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "MarcaMercaderia{" + "ID=" + id + ", marca=" + nome + '}';
    }

}
