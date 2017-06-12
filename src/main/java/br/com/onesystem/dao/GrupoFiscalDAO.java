package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class GrupoFiscalDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public GrupoFiscalDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public GrupoFiscalDAO buscarGrupos() {
        consulta += "select g from GrupoFiscal g where g.id > 0 ";
        return this;
    }

    public GrupoFiscalDAO porId(Long id) {
        consulta += " and g.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoFiscalDAO porNome(GrupoFiscal grupo) {
        consulta += " and g.nome = :pNome ";
        parametros.put("pNome", grupo.getNome());
        return this;
    }

    public List<GrupoFiscal> listaDeResultados() {
        List<GrupoFiscal> resultado = new ArmazemDeRegistros<GrupoFiscal>(GrupoFiscal.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public GrupoFiscal resultado() throws DadoInvalidoException {
        try {
            GrupoFiscal resultado = new ArmazemDeRegistros<GrupoFiscal>(GrupoFiscal.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
