package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class DespesaBV implements Serializable {

    private Long id;
    private String nome;
    private GrupoFinanceiro grupoFinanceiro;

    public DespesaBV(Despesa despesaSelecionada) {
        this.id = despesaSelecionada.getId();
        this.nome = despesaSelecionada.getNome();
        this.grupoFinanceiro = despesaSelecionada.getGrupoFinanceiro();
    }

    public DespesaBV() {
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

    public Despesa construir() throws DadoInvalidoException {
        return new Despesa(null, nome, grupoFinanceiro);
    }

    public Despesa construirComID() throws DadoInvalidoException {
        return new Despesa(id, nome, grupoFinanceiro);
    }
}
