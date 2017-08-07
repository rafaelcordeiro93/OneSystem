/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
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

/**
 *
 * @author Rafael-Pc
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_GRUPOFINANCEIRO",
        sequenceName = "SEQ_GRUPOFINANCEIRO")
public class GrupoFinanceiro implements Serializable {

    private static final long serialVersionUID = 8167702146233639964L;

    @Id
    @GeneratedValue(generator = "SEQ_GRUPOFINANCEIRO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, min = 4, message = "{nome_lenght}")
    private String nome;

    @NotNull(message = "{tipo_financeiro_not_null}")
    @Enumerated(EnumType.STRING)
    private NaturezaFinanceira naturezaFinanceira;

    @OneToMany(mappedBy = "grupoFinanceiro")
    private List<TipoDespesa> listaDeDespesas;

    @OneToMany(mappedBy = "grupoFinanceiro")
    private List<TipoDespesa> listaDeReceitas;

    @NotNull(message = "{classificacao_financeira_not_null}")
    @Enumerated(EnumType.STRING)
    private ClassificacaoFinanceira classificacaoFinanceira;

    public GrupoFinanceiro() {
    }

    public GrupoFinanceiro(Long id, String nome, NaturezaFinanceira naturezaFinanceira,
            ClassificacaoFinanceira classificacaoFinanceira) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.naturezaFinanceira = naturezaFinanceira;
        this.classificacaoFinanceira = classificacaoFinanceira;
        ehValido();
    }

    public List<TipoDespesa> getListaDeDespesas() {
        return listaDeDespesas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public NaturezaFinanceira getNaturezaFinanceira() {
        return naturezaFinanceira;
    }

    public ClassificacaoFinanceira getClassificacaoFinanceira() {
        return classificacaoFinanceira;
    }

    public List<TipoDespesa> getListaDeReceitas() {
        return listaDeReceitas;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "naturezaFinanceira", "classificacaoFinanceira");
        new ValidadorDeCampos<GrupoFinanceiro>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof GrupoFinanceiro)) {
            return false;
        }
        GrupoFinanceiro outro = (GrupoFinanceiro) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
