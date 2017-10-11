package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.MetodoInacessivelRelatorio;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
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

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CEP",
        sequenceName = "SEQ_CEP")
public class Cep implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CEP")
    private Long id;
    @NotNull(message = "{cep_not_null}")
    @Length(min = 8, max = 9, message = "{cep_lenght}")
    @Column(nullable = false, length = 20, unique = true)
    private String cep;
    @OneToMany(mappedBy = "cep", fetch = FetchType.LAZY)
    private List<Pessoa> listadePessoas;
    @NotNull(message = "{cidade_not_null}")
    @ManyToOne(optional = false)
    private Cidade cidade;

    public Cep() {
    }

    public Cep(Long id, String cep, Cidade cidade) throws DadoInvalidoException {
        this.id = id;
        this.cep = cep;
        this.cidade = cidade;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    @MetodoInacessivelRelatorio
    public String getCepCidadeEstadoPaisFormatado() {
        String str = "";
        if (cep != null) {
            str += cep;
        }
        if (cidade != null) {
            if (cidade.getNome() != null) {
                str += " - ";
            }
            str += cidade.getNome();
        }
        if (cidade != null && cidade.getEstado() != null) {
            if (cidade.getEstado().getSigla() != null) {
                str += " - ";
            }
            str += cidade.getEstado().getSigla();
        }
        if (cidade != null && cidade.getEstado() != null &&  cidade.getEstado().getPais() != null) {
            str += " - ";
            str += cidade.getEstado().getPais().getNome();
        }
        return str;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("cep", "cidade");
        new ValidadorDeCampos<Cep>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cep)) {
            return false;
        }
        Cep outro = (Cep) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cidade{" + "id=" + id + ", cep=" + cep + '}';
    }

}
