package br.com.onesystem.dao;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class GrupoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public GrupoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public GrupoDAO buscarGrupos() {
        consulta += "select g from Grupo g where g.id > 0 ";
        return this;
    }

    public GrupoDAO porId(Long id) {
        consulta += " and g.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoDAO porNome(Grupo grupo) {
        consulta += " and g.nome = :pNome ";
        parametros.put("pNome", grupo.getNome());
        return this;
    }

    public List<Grupo> listaDeResultados() {
        List<Grupo> resultado = new ArmazemDeRegistros<Grupo>(Grupo.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Grupo resultado() throws DadoInvalidoException {
        try {
            Grupo resultado = new ArmazemDeRegistros<Grupo>(Grupo.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
