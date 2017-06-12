package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class ColunaBuilder {

    private Long ID;
    private String key;
    private String nome;
    private ModeloDeRelatorio modeloDeRelatorio;

    public ColunaBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public ColunaBuilder comKey(String key) {
        this.key = key;
        return this;
    }

    public ColunaBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ColunaBuilder comModeloDeRelatorio(ModeloDeRelatorio modeloDeRelatorio) {
        this.modeloDeRelatorio = modeloDeRelatorio;
        return this;
    }

    public Coluna construir() throws DadoInvalidoException {
        return new Coluna(ID, key, modeloDeRelatorio);
    }

}
