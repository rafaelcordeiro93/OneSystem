package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ReceitaBV implements Serializable {

    private Long id;
    private String nome;
    private GrupoFinanceiro grupoFinanceiro;

    public ReceitaBV(TipoReceita receitaSelecionada) {
        this.id = receitaSelecionada.getId();
        this.nome = receitaSelecionada.getNome();
        this.grupoFinanceiro = receitaSelecionada.getGrupoFinanceiro();
    }

    public ReceitaBV() {
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
