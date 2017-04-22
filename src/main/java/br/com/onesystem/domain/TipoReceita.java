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
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_RECEITA",
        name = "SEQ_RECEITA")
public class TipoReceita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEITA")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, min = 2, message = "{nome_lenght}")
    private String nome;
    @NotNull(message = "{grupo_financeiro_not_null}")
    @ManyToOne
    private GrupoFinanceiro grupoFinanceiro;
    @OneToMany(mappedBy = "receita")
    private List<Baixa> receitas;
    @OneToMany(mappedBy = "tipoReceita")
    private List<ReceitaProvisionada> receitasProvisionadas;
    @OneToMany(mappedBy = "vendaAVista")
    private List<Operacao> vendasAVista;
    @OneToMany(mappedBy = "vendaAPrazo")
    private List<Operacao> vendasAPrazo;
    @OneToMany(mappedBy = "servicoAVista")
    private List<Operacao> servicosAVista;
    @OneToMany(mappedBy = "servicoAPrazo")
    private List<Operacao> servicosAPrazo;
    @OneToMany(mappedBy = "receitaFrete")
    private List<Operacao> receitasFretes;
    @OneToMany(mappedBy = "receita")
    private List<ReceitaEventual> receitasEventuais;

    public TipoReceita() {
    }

    public TipoReceita(Long id, String nome, GrupoFinanceiro grupoFinanceiro) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.grupoFinanceiro = grupoFinanceiro;
        ehValido();
    }

    public List<Baixa> getReceitas() {
        return receitas;
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

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "grupoFinanceiro");
        new ValidadorDeCampos<TipoReceita>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Conta)) {
            return false;
        }
        TipoReceita outro = (TipoReceita) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Receita{" + "codigo=" + id + ", nome=" + nome + ", grupoFinanceiro=" + grupoFinanceiro + '}';
    }

}
