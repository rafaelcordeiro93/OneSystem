package br.com.onesystem.domain;

import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_MOTIVO",
        sequenceName = "SEQ_MOTIVO")
public class Motivo implements Serializable {

   
    @Id
    @GeneratedValue(generator = "SEQ_MOTIVO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{nome_not_null}")
    @Length(min = 0, max = 80, message = "{nome_lenght}")
    @Column(nullable = false, length = 80)
    private String nome;

    public Motivo() {
    }

    public Motivo(Long id, String nome) throws DadoInvalidoException {
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
        new ValidadorDeCampos<Motivo>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Motivo)) {
            return false;
        }
        Motivo outro = (Motivo) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Motivo{" + "id=" + id + ", nome=" + nome + '}';
    }

}
