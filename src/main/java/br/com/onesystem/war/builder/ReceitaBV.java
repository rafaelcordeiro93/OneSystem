package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ReceitaBV implements Serializable {

    private Long id;
    private String nome;
    private GrupoFinanceiro grupoFinanceiro;

    public ReceitaBV(Receita receitaSelecionada) {
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

    public Receita construir() throws DadoInvalidoException {
        return new Receita(null, nome, grupoFinanceiro);
    }

    public Receita construirComID() throws DadoInvalidoException {
        return new Receita(id, nome, grupoFinanceiro);
    }
}
