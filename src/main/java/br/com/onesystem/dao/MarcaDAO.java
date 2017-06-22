package br.com.onesystem.dao;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

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

    public MarcaDAO porId(Long id) {
        consulta += " and m.id = :mId ";
        parametros.put("mId", id);
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

    public Marca resultado() throws DadoInvalidoException {
        try {
            Marca resultado = new ArmazemDeRegistros<Marca>(Marca.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
