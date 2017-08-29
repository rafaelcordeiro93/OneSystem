package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.domain.builder.MotivoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class MotivoBV implements Serializable, BuilderView<Motivo> {

    private Long id;
    private String nome;

    public MotivoBV(Motivo m) {
        this.id = m.getId();
        this.nome = m.getNome();
    }

    public MotivoBV() {
    }

    @Override
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

    public Motivo construir() throws DadoInvalidoException {
        return new MotivoBuilder().comNome(nome).construir();
    }

    public Motivo construirComID() throws DadoInvalidoException {
        return new MotivoBuilder().comID(id).comNome(nome).construir();
    }

}
