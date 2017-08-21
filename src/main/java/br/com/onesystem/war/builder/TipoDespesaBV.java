package br.com.onesystem.war.builder;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class TipoDespesaBV implements Serializable, BuilderView<TipoDespesa> {
    
    private Long id;
    private String nome;
    private GrupoFinanceiro grupoFinanceiro;
    
    public TipoDespesaBV(TipoDespesa despesaSelecionada) {
        this.id = despesaSelecionada.getId();
        this.nome = despesaSelecionada.getNome();
        this.grupoFinanceiro = despesaSelecionada.getGrupoFinanceiro();
    }
    
    public TipoDespesaBV() {
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
    
    public GrupoFinanceiro getGrupoFinanceiro() {
        return grupoFinanceiro;
    }
    
    public void setGrupoFinanceiro(GrupoFinanceiro grupoFinanceiro) {
        this.grupoFinanceiro = grupoFinanceiro;
    }
    
    public TipoDespesa construir() throws DadoInvalidoException {
        return new TipoDespesa(null, nome, grupoFinanceiro);
    }
    
    public TipoDespesa construirComID() throws DadoInvalidoException {
        return new TipoDespesa(id, nome, grupoFinanceiro);
    }
}
