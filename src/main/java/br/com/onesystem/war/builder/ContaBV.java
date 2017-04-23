package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class ContaBV implements Serializable, BuilderView<Conta> {

    private Long id;
    private String nome;
    private Banco banco;
    private Moeda moeda;

    public ContaBV(Conta contaSelecionada) {
        this.id = contaSelecionada.getId();
        this.banco = contaSelecionada.getBanco();
        this.nome = contaSelecionada.getNome();
        this.moeda = contaSelecionada.getMoeda();
    }

    public ContaBV() {
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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Conta construir() throws DadoInvalidoException {
        return new Conta(null, nome, banco, moeda);
    }

    public Conta construirComID() throws DadoInvalidoException {
        return new Conta(id, nome, banco, moeda);
    }
}
