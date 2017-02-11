package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.builder.ColunaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class ColunaBV implements Serializable {

    private Long id;
    private String key;

    public ColunaBV(Coluna colunaSelecionada) {
        this.id = colunaSelecionada.getId();
        this.key = colunaSelecionada.getKey();
    }

    public ColunaBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Coluna construir() throws DadoInvalidoException {
        return new ColunaBuilder().comKey(key).construir();
    }

    public Coluna construirComID() throws DadoInvalidoException {
        return new ColunaBuilder().comID(id).comKey(key).construir();
    }
}
