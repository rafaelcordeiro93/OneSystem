package br.com.onesystem.dao;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormaDeRecebimentoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public FormaDeRecebimentoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public FormaDeRecebimentoDAO buscarFormasDeRecebimento() {
        consulta += "select f from FormaDeRecebimento f where f.id > 0 ";
        return this;
    }

    public FormaDeRecebimentoDAO ativas() {
        consulta += " and f.ativo = :pAtiva ";
        parametros.put("pAtiva", true);
        return this;
    }

    public List<FormaDeRecebimento> listaDeResultados() {
        List<FormaDeRecebimento> resultado = new ArmazemDeRegistros<FormaDeRecebimento>(FormaDeRecebimento.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
