package br.com.onesystem.domain;

import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_BANCO",
        sequenceName = "SEQ_BANCO")
public class Banco implements Serializable {

    private static final long serialVersionUID = -1741096381813060074L;

    @Id
    @GeneratedValue(generator = "SEQ_BANCO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(max = 80, message = "{codigo_lenght}")
    @Column(nullable = true, length = 80)
    private String codigo;

    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    @Column(nullable = false, length = 80)
    private String nome;

    @Length(min = 4, max = 80, message = "{site_lenght}")
    @Column(nullable = true, length = 80)
    private String site;

    @OneToMany(mappedBy = "banco")
    private List<Conta> contas;

    public Banco() {
    }

    public Banco(Long id, String codigo, String nome, String site) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.site = site;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getSite() {
        return site;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("codigo", "nome", "site");
        new ValidadorDeCampos<Banco>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Banco)) {
            return false;
        }
        Banco outro = (Banco) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
