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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_GRUPO",
        sequenceName = "SEQ_GRUPO")
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_GRUPO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 2, max = 60, message = "{nome_length}")
    @Column(length = 60, nullable = false)
    private String nome;
    @OneToMany(mappedBy = "grupo")
    private List<Item> itens;

    public Grupo() {
    }

    public Grupo(Long id, String grupo) throws DadoInvalidoException {
        this.id = id;
        this.nome = grupo;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Grupo>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Grupo)) {
            return false;
        }
        Grupo outro = (Grupo) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
