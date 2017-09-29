package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.builder.DepositoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class DepositoBV implements Serializable, BuilderView<Deposito> {

    private Long id;
    private String nome;  

    public DepositoBV(Deposito depositoSelecionada) {
        this.id = depositoSelecionada.getId();
        this.nome = depositoSelecionada.getNome();
    }

    public DepositoBV() {
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

    public Deposito construir() throws DadoInvalidoException {
        return new DepositoBuilder().comNome(nome).construir();
    }

    public Deposito construirComID() throws DadoInvalidoException {
        return new DepositoBuilder().comID(id).comNome(nome).construir();
    }
}
