package br.com.onesystem.dao;

import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class FaturaLegadaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public FaturaLegadaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public FaturaLegadaDAO buscarFaturaLegadas() {
        consulta += "select b from FaturaLegada b where b.id > 0 ";
        return this;
    }

    public FaturaLegadaDAO porId(Long id) {
        consulta += " and b.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

    public List<FaturaLegada> listaDeResultados() {
        List<FaturaLegada> resultado = new ArmazemDeRegistros<FaturaLegada>(FaturaLegada.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public FaturaLegada resultado() throws DadoInvalidoException {
        try {
            FaturaLegada resultado = new ArmazemDeRegistros<FaturaLegada>(FaturaLegada.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
