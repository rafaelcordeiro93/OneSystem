package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoBandeira;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_MOEDA",
        sequenceName = "SEQ_MOEDA")
public class Moeda implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_MOEDA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @CharacterType(value = CaseType.LETTER_SPACE, message = "{nome_type_letter_space}")
    @Length(min = 3, max = 30, message = "{nome_lenght}")
    @Column(nullable = false, length = 30, unique = true)
    private String nome;
    @NotNull(message = "{sigla_not_null}")
    @Length(min = 1, max = 3, message = "{sigla_length}")
    @Column(nullable = false, length = 3)
    private String sigla;
    @Enumerated(EnumType.STRING)
    private TipoBandeira bandeira;   
    @OneToMany(mappedBy = "moeda")
    private List<Conta> listaContas;
    @OneToMany(mappedBy = "origem")
    private List<ContratoDeCambio> listaCambioOrigem;
    @OneToMany(mappedBy = "destino")
    private List<ContratoDeCambio> listaCambioDestino;
    @OneToMany(mappedBy = "moeda")
    private List<ValoresAVista> formasDeRecebimentoOuPagamento;

    public Moeda() {
    }

    public Moeda(Long id, String nome, String sigla, TipoBandeira bandeira) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.bandeira = bandeira;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public List<Conta> getListaContas() {
        return listaContas;
    }

    public TipoBandeira getBandeira() {
        return bandeira;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Moeda)) {
            return false;
        }
        Moeda outro = (Moeda) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "sigla");
        new ValidadorDeCampos<Moeda>().valida(this, campos);
    }

}
