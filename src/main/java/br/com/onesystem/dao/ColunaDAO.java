package br.com.onesystem.dao;

import br.com.onesystem.domain.Coluna;
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

public class ColunaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ColunaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ColunaDAO buscarColuna() {
        consulta += "select a from Coluna a where a.key != null ";
        return this;
    }

    public ColunaDAO porNome(Coluna coluna) {
        consulta += " and a.nome = :pNome ";
        parametros.put("pNome", coluna.getNome());
        return this;
    }

    public List<Coluna> listaDeResultados() {
        List<Coluna> resultado = new ArmazemDeRegistros<Coluna>(Coluna.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public List<String> listaDeNomes() {
        List<String> nomes = new ArrayList<String>();
        List<Coluna> resultado = new ArmazemDeRegistros<Coluna>(Coluna.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        for (Coluna tr : resultado) {
            nomes.add(tr.getNome());
        }
        limpar();
        return nomes;
    }

    public Coluna resultado() throws DadoInvalidoException {
        try {
            Coluna resultado = new ArmazemDeRegistros<Coluna>(Coluna.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
