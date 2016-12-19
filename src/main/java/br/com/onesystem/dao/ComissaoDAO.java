package br.com.onesystem.dao;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComissaoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ComissaoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ComissaoDAO buscarComissaos() {
        consulta += "select c from Comissao c where c.id > 0 ";
        return this;
    }

    public ComissaoDAO porNome(Comissao comissao) {
        consulta += " and c.nome = :cNome ";
        parametros.put("cNome", comissao.getNome());
        return this;
    }

    public List<Comissao> listaDeResultados() {
        List<Comissao> resultado = new ArmazemDeRegistros<Comissao>(Comissao.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
