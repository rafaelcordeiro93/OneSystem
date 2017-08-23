package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class GrupoDePrivilegioDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public GrupoDePrivilegioDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public GrupoDePrivilegioDAO buscarGrupos() {
        consulta += "select g from GrupoDePrivilegio g where g.id > 0 ";
        return this;
    }

    public GrupoDePrivilegioDAO porId(Long id) {
        consulta += " and g.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoDePrivilegioDAO porNome(GrupoDePrivilegio grupoDePrivilegio) {
        consulta += " and g.nome = :pNome ";
        parametros.put("pNome", grupoDePrivilegio.getNome());
        return this;
    }

    public List<GrupoDePrivilegio> listaDeResultados() {
        List<GrupoDePrivilegio> resultado = new ArmazemDeRegistros<GrupoDePrivilegio>(GrupoDePrivilegio.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public GrupoDePrivilegio resultado() throws DadoInvalidoException {
        try {
            GrupoDePrivilegio resultado = new ArmazemDeRegistros<GrupoDePrivilegio>(GrupoDePrivilegio.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
