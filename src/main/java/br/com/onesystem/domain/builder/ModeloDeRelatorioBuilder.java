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
    private List<Coluna> colunas;
    private List<FiltroDeRelatorio> filtroDeRelatorio;
    private TipoRelatorio tipoRelatorio;

    public ModeloDeRelatorioBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ModeloDeRelatorioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ModeloDeRelatorioBuilder comColunas(List<Coluna> colunas) {
        this.colunas = colunas;
        return this;
    }

    public ModeloDeRelatorioBuilder comTipoRelatorio(TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
        return this;
    }

    public ModeloDeRelatorioBuilder comFiltroDeRelatorio(List<FiltroDeRelatorio> filtroDeRelatorio) {
        this.filtroDeRelatorio = filtroDeRelatorio;
        return this;
    }

    public ModeloDeRelatorio construir() throws DadoInvalidoException {
        return new ModeloDeRelatorio(id, nome, colunas, filtroDeRelatorio, tipoRelatorio);
    }

}
