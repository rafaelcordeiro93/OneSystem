package br.com.onesystem.dao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class AjusteDeEstoqueDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public AjusteDeEstoqueDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public AjusteDeEstoqueDAO buscarAjustesDeEstoque() {
        consulta += "select a from AjusteDeEstoque a where a.id != 0 ";
        return this;
    }

    public AjusteDeEstoqueDAO porItem(Item item) {
        consulta += " and a.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

    public AjusteDeEstoqueDAO porId(Long id) {
        consulta += " and a.id = :aId ";
        parametros.put("aId", id);
        return this;
    }

    public AjusteDeEstoqueDAO ordenadoPorEmissaoDescrescente() {
        consulta += " order by a.emissao desc ";
        return this;
    }

    public List<AjusteDeEstoque> listaDeResultados() {
        List<AjusteDeEstoque> resultado = new ArmazemDeRegistros<AjusteDeEstoque>(AjusteDeEstoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public AjusteDeEstoque resultado() throws DadoInvalidoException {
        try {
            AjusteDeEstoque resultado = new ArmazemDeRegistros<AjusteDeEstoque>(AjusteDeEstoque.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
