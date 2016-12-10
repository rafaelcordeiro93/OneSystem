package br.com.onesystem.dao;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarcaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public MarcaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public MarcaDAO buscarMarcas() {
        consulta += "select m from Marca m where m.id > 0 ";
        return this;
    }

    public MarcaDAO porNome(Marca marca) {
        consulta += " and m.nome = :mNome ";
        parametros.put("mNome", marca.getNome());
        return this;
    }

    public List<Marca> listaDeResultados() {
        List<Marca> resultado = new ArmazemDeRegistros<Marca>(Marca.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
