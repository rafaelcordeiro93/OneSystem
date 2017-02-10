package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static javax.management.Query.gt;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_MODELODERELATORIO",
        sequenceName = "SEQ_MODELODERELATORIO")
public class ModeloDeRelatorio implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_MODELODERELATORIO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 2, max = 40, message = "{nome_lenght}")
    @Column(length = 60, nullable = false)
    private String nome;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Coluna", joinColumns = @JoinColumn(name = "id"))
    private List<String> key;
    @NotNull(message = "{tipo_relatorio_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoRelatorio tipoRelatorio;

    public ModeloDeRelatorio() {
    }

    public ModeloDeRelatorio(Long id, String nome, List<String> listaDeCampos, TipoRelatorio tipoRelatorio) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.key = listaDeCampos;
        this.tipoRelatorio = tipoRelatorio;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome");
        new ValidadorDeCampos<ModeloDeRelatorio>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getKey() {
        return key;
    }

    public TipoRelatorio getTipoRelatorio() {
        return tipoRelatorio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModeloDeRelatorio other = (ModeloDeRelatorio) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TemplateRelatorios{" + "id=" + id + ", nome=" + nome + ", listaDeCampos=" + key + ", tipoRelatorio=" + tipoRelatorio + '}';
    }

}
