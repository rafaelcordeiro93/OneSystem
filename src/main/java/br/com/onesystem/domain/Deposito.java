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
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_DEPOSITO",
        sequenceName = "SEQ_DEPOSITO")
public class Deposito implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_DEPOSITO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 2, max = 60, message = "{nome_length}")
    @Column(length = 60, nullable = false)
    private String nome;
    @OneToMany(mappedBy = "deposito")
    private List<AjusteDeEstoque> listadeAjuste;
    @OneToMany(mappedBy = "deposito") 
    private List<ItemPorDeposito> itensPorDeposito;


    public Deposito() {
    }
    
    public Deposito(Long id, String deposito) throws DadoInvalidoException {
        this.id = id;
        this.nome = deposito;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<Deposito>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Deposito)) {
            return false;
        }
        Deposito outro = (Deposito) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Deposito{" + "id=" + id + ", nome=" + nome + '}';
    }

}