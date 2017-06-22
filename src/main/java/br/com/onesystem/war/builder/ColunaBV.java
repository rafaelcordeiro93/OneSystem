package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.builder.ColunaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.Objects;

public class ColunaBV implements Serializable {

    private Long id;
    private String key;
    private String nome;
    private ModeloDeRelatorio modeloDeRelatorio;

    public ColunaBV(Coluna colunaSelecionada) {
        this.id = colunaSelecionada.getId();
        this.key = colunaSelecionada.getKey();
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        return new ColunaBuilder().comKey(key).comNome(nome).comModeloDeRelatorio(modeloDeRelatorio).construir();
    }

    public Coluna construirComID() throws DadoInvalidoException {
        return new ColunaBuilder().comID(id).comKey(key).comNome(nome).comModeloDeRelatorio(modeloDeRelatorio).construir();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColunaBV other = (ColunaBV) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }

}
