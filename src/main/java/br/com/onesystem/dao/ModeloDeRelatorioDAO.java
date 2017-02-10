package br.com.onesystem.dao;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class ModeloDeRelatorioDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ModeloDeRelatorioDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ModeloDeRelatorioDAO buscarModeloDeRelatorio() {
        consulta += "select a from TemplateRelatorios a where a.id != 0 ";
        return this;
    }

    public ModeloDeRelatorioDAO porNome(ModeloDeRelatorio templateRelatorios) {
        consulta += " and a.nome = :pNome ";
        parametros.put("pNome", templateRelatorios.getNome());
        return this;
    }

    public ModeloDeRelatorioDAO porId(Long id) {
        consulta += " and a.id = :aId ";
        parametros.put("aId", id);
        return this;
    }

    public ModeloDeRelatorioDAO porTipoRelatorio(TipoRelatorio tipo) {
        consulta += " and a.tipoRelatorio = :aTipo ";
        parametros.put("aTipo", tipo);
        return this;
    }

    public List<ModeloDeRelatorio> listaDeResultados() {
        List<ModeloDeRelatorio> resultado = new ArmazemDeRegistros<ModeloDeRelatorio>(ModeloDeRelatorio.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public List<String> listaDeNomes() {
        List<String> nomes = new ArrayList<String>();
        List<ModeloDeRelatorio> resultado = new ArmazemDeRegistros<ModeloDeRelatorio>(ModeloDeRelatorio.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        for (ModeloDeRelatorio tr : resultado) {
            nomes.add(tr.getNome());
        }
        limpar();
        return nomes;
    }

    public ModeloDeRelatorio resultado() throws DadoInvalidoException {
        try {
            ModeloDeRelatorio resultado = new ArmazemDeRegistros<ModeloDeRelatorio>(ModeloDeRelatorio.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
