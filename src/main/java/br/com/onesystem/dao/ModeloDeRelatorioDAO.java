package br.com.onesystem.dao;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.valueobjects.TipoRelatorio;

public class ModeloDeRelatorioDAO extends GenericDAO<ModeloDeRelatorio> {

    public ModeloDeRelatorioDAO() {
        super(ModeloDeRelatorio.class);
    }

    public ModeloDeRelatorioDAO porNome(ModeloDeRelatorio templateRelatorios) {
        where += " and modeloDeRelatorio.nome = :pNome ";
        parametros.put("pNome", templateRelatorios.getNome());
        return this;
    }

    public ModeloDeRelatorioDAO porId(Long id) {
        where += " and modeloDeRelatorio.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public ModeloDeRelatorioDAO porTipoRelatorio(TipoRelatorio tipo) {
        where += " and modeloDeRelatorio.tipoRelatorio = :pTipo ";
        parametros.put("pTipo", tipo);
        return this;
    }

}
