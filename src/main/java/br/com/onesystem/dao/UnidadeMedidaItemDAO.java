package br.com.onesystem.dao;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

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

    public UnidadeMedidaItemDAO porId(Long id) {
        consulta += " and d.id = :dId ";
        parametros.put("dId", id);
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

    public UnidadeMedidaItem resultado() throws DadoInvalidoException {
        try {
            UnidadeMedidaItem resultado = new ArmazemDeRegistros<UnidadeMedidaItem>(UnidadeMedidaItem.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
