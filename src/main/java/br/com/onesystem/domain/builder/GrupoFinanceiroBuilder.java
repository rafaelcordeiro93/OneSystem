package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;

/**
 *
 * @author Rafael
 */
public class GrupoFinanceiroBuilder {

    private Long id;
    private String nome;
    private NaturezaFinanceira naturezaFinanceira;
    private ClassificacaoFinanceira classificacaoFinanceira;

    public GrupoFinanceiroBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public GrupoFinanceiroBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GrupoFinanceiroBuilder comNaturezaFinanceira(NaturezaFinanceira naturezaFinanceira) {
        this.naturezaFinanceira = naturezaFinanceira;
        return this;
    }

    public GrupoFinanceiroBuilder comClassificacaoFinanceira(ClassificacaoFinanceira classificacaoFinanceira) {
        this.classificacaoFinanceira = classificacaoFinanceira;
        return this;
    }

    public GrupoFinanceiro construir() throws DadoInvalidoException {
        return new GrupoFinanceiro(id, nome, naturezaFinanceira, classificacaoFinanceira);
    }

}
