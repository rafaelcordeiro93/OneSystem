package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class ItemDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ItemDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ItemDAO buscarItems() {
        consulta += "select i from Item i where i.id > 0 ";
        return this;
    }

    public ItemDAO porItem(Item item) {
        if (item != null) {
            consulta += " and i.id = :iId ";
            parametros.put("iId", item.getId());
        }
        return this;
    }

    public ItemDAO porId(Long id) {
        if (id != null) {
            consulta += " and i.id = :iId ";
            parametros.put("iId", id);
        }
        return this;
    }

    public List<Item> listaDeResultados() {
        List<Item> resultado = new ArmazemDeRegistros<Item>(Item.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Item resultado() throws DadoInvalidoException {
        try {
            Item resultado = new ArmazemDeRegistros<Item>(Item.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
