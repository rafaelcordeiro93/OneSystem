package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ModeloDeRelatorioBuilder {

    private Long id;
    private String nome;
    private TipoRelatorio tipoRelatorio;

    public ModeloDeRelatorioBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ModeloDeRelatorioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ModeloDeRelatorioBuilder comTipoRelatorio(TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
        return this;
    }

    public ModeloDeRelatorio construir() throws DadoInvalidoException {
        return new ModeloDeRelatorio(id, nome, tipoRelatorio);
    }

}
