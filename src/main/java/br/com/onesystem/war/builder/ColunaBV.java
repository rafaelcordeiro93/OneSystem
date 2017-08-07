package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.builder.ColunaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Objects;

public class ColunaBV implements Serializable {

    private Long id;
    private String nome;
    private ModeloDeRelatorio modeloDeRelatorio;

    public ColunaBV(Coluna colunaSelecionada) {
        this.id = colunaSelecionada.getId();
        this.nome = colunaSelecionada.getNome();
        this.modeloDeRelatorio = colunaSelecionada.getModelo();
    }

    public ColunaBV() {
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

    public ModeloDeRelatorio getModeloDeRelatorio() {
        return modeloDeRelatorio;
    }

    public void setModeloDeRelatorio(ModeloDeRelatorio modeloDeRelatorio) {
        this.modeloDeRelatorio = modeloDeRelatorio;
    }

    public Coluna construir() throws DadoInvalidoException {
        return new ColunaBuilder().comNome(nome).comModeloDeRelatorio(modeloDeRelatorio).construir();
    }

    public Coluna construirComID() throws DadoInvalidoException {
        return new ColunaBuilder().comID(id).comNome(nome).comModeloDeRelatorio(modeloDeRelatorio).construir();
    }

}
