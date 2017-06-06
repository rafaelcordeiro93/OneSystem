package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;

public class GrupoFinanceiroBV implements Serializable {

    private static final long serialVersionUID = 6686124108160060627L;

    private Long id;
    private String nome;
    private NaturezaFinanceira naturezaFinanceira = NaturezaFinanceira.RECEITA;
    private boolean exibirNoDRE = true;
    private ClassificacaoFinanceira classificacaoFinanceira;

    public GrupoFinanceiroBV(GrupoFinanceiro grupoFinanceiroSelecionado) {
        this.id = grupoFinanceiroSelecionado.getId();
        this.nome = grupoFinanceiroSelecionado.getNome();
        this.naturezaFinanceira = grupoFinanceiroSelecionado.getNaturezaFinanceira();
        this.classificacaoFinanceira = grupoFinanceiroSelecionado.getClassificacaoFinanceira();
    }

    public GrupoFinanceiroBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public NaturezaFinanceira getNaturezaFinanceira() {
        return naturezaFinanceira;
    }

    public void setNaturezaFinanceira(NaturezaFinanceira naturezaFinanceira) {
        this.naturezaFinanceira = naturezaFinanceira;
    }

    public boolean isExibirNoDRE() {
        return exibirNoDRE;
    }

    public void setExibirNoDRE(boolean exibirNoDRE) {
        this.exibirNoDRE = exibirNoDRE;
    }

    public ClassificacaoFinanceira getClassificacaoFinanceira() {
        return classificacaoFinanceira;
    }

    public void setClassificacaoFinanceira(ClassificacaoFinanceira classificacaoFinanceira) {
        this.classificacaoFinanceira = classificacaoFinanceira;
    }

    public GrupoFinanceiro construir() throws DadoInvalidoException {
        return new GrupoFinanceiro(null, nome, naturezaFinanceira, classificacaoFinanceira);
    }

    public GrupoFinanceiro construirComID() throws DadoInvalidoException {
        return new GrupoFinanceiro(id, nome, naturezaFinanceira, classificacaoFinanceira);
    }
}
