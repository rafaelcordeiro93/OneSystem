package br.com.onesystem.war.builder;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class TipoReceitaBV implements Serializable, BuilderView<TipoReceita> {
    
    private Long id;
    private String nome;
    private GrupoFinanceiro grupoFinanceiro;
    
    public TipoReceitaBV(TipoReceita despesaSelecionada) {
        this.id = despesaSelecionada.getId();
        this.nome = despesaSelecionada.getNome();
        this.grupoFinanceiro = despesaSelecionada.getGrupoFinanceiro();
    }
    
    public TipoReceitaBV() {
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
    
    public TipoReceita construir() throws DadoInvalidoException {
        return new TipoReceita(null, nome, grupoFinanceiro);
    }
    
    public TipoReceita construirComID() throws DadoInvalidoException {
        return new TipoReceita(id, nome, grupoFinanceiro);
    }
}
