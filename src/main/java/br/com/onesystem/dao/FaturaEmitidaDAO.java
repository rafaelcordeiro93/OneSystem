package br.com.onesystem.dao;

import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class FaturaEmitidaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public FaturaEmitidaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public FaturaEmitidaDAO buscarFaturaEmitidas() {
        consulta += "select b from FaturaEmitida b where b.id > 0 ";
        return this;
    }

    public FaturaEmitidaDAO porId(Long id) {
        consulta += " and b.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

    public List<FaturaEmitida> listaDeResultados() {
        List<FaturaEmitida> resultado = new ArmazemDeRegistros<FaturaEmitida>(FaturaEmitida.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public FaturaEmitida resultado() throws DadoInvalidoException {
        try {
            FaturaEmitida resultado = new ArmazemDeRegistros<FaturaEmitida>(FaturaEmitida.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
