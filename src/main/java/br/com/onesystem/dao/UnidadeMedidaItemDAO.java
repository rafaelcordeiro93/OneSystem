package br.com.onesystem.dao;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnidadeMedidaItemDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public UnidadeMedidaItemDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public UnidadeMedidaItemDAO buscarUnidadeMedidaItem() {
        consulta += "select d from UnidadeMedidaItem d where d.id > 0 ";
        return this;
    }

    public UnidadeMedidaItemDAO porNome(UnidadeMedidaItem unidadeMedidaItem) {
        consulta += " and d.nome = :dNome ";
        parametros.put("dNome", unidadeMedidaItem.getNome());
        return this;
    }

    public List<UnidadeMedidaItem> listaDeResultados() {
        List<UnidadeMedidaItem> resultado = new ArmazemDeRegistros<UnidadeMedidaItem>(UnidadeMedidaItem.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
