package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @Length(max = 60, message = "{nome_lenght}")
    @Column(length = 60, nullable = false)
    private String nome;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "modelo") 
    private List<Coluna> colunas;
    @NotNull(message = "{tipo_relatorio_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoRelatorio tipoRelatorio;

    public ModeloDeRelatorio() {
    }

    public ModeloDeRelatorio(Long id, String nome, List<Coluna> colunas, TipoRelatorio tipoRelatorio) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.colunas = colunas;
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

    public TipoRelatorio getTipoRelatorio() {
        return tipoRelatorio;
    }

    public List<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
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
        return "TemplateRelatorios{" + "id=" + id + ", nome=" + nome + ", colunas=" + colunas + ", tipoRelatorio=" + tipoRelatorio + '}';
    }

}
