package br.com.onesystem.domain;

import br.com.onesystem.services.CharacterType;
import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
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
 * @author Rafael-Pc
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_TIPODESPESA",
        name = "SEQ_TIPODESPESA")
public class TipoDespesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPODESPESA")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, min = 2, message = "{nome_lenght}")
    private String nome;
    @NotNull(message = "{grupo_financeiro_not_null}")
    @ManyToOne
    private GrupoFinanceiro grupoFinanceiro;
    @OneToMany(mappedBy = "despesa")
    private List<Baixa> despesas;
    @OneToMany(mappedBy = "tipoDespesa")
    private List<DespesaProvisionada> despesasProvisionadas;
    @OneToMany(mappedBy = "despesaCMV")
    private List<Operacao> despesasCMV;
    @OneToMany(mappedBy = "compraAVista")
    private List<Operacao> comprasAVista;
    @OneToMany(mappedBy = "compraAPrazo")
    private List<Operacao> comprasAPrazo;
    @OneToMany(mappedBy = "tipoDespesa")
    private List<DespesaEventual> despesasEventuals;
    @OneToMany(mappedBy = "despesa")
    private List<LancamentoBancario> lancamentoBancario;

    public TipoDespesa() {
    }

    public TipoDespesa(Long id, String nome, GrupoFinanceiro grupoFinanceiro) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.grupoFinanceiro = grupoFinanceiro;
        ehValido();
    }

    public List<Baixa> getDespesas() {
        return despesas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public GrupoFinanceiro getGrupoFinanceiro() {
        return grupoFinanceiro;
    }

    public List<DespesaProvisionada> getDespesasProvisionadas() {
        return despesasProvisionadas;
    }

    public List<LancamentoBancario> getLancamentoBancario() {
        return lancamentoBancario;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "grupoFinanceiro");
        new ValidadorDeCampos<TipoDespesa>().valida(this, campos);
    }

    @Override
    public String toString() {
        return "Despesa{" + "codigo=" + id + ", nome=" + nome + ", grupoFinanceiro=" + grupoFinanceiro + '}';
    }

}
