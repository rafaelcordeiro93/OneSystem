package br.com.onesystem.dao;

import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class OrcamentoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public OrcamentoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public OrcamentoDAO buscarOrcamento() {
        consulta += "select o from Orcamento o where o.id > 0 ";
        return this;
    }

    public OrcamentoDAO porId(Long id) {
        consulta += " and o.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public OrcamentoDAO porEstado(EstadoDeOrcamento estado) {
        consulta += " and o.estado = :oEstado ";
        parametros.put("oEstado", estado);
        return this;
    }

    public List<Orcamento> listaDeResultados() {
        List<Orcamento> resultado = new ArmazemDeRegistros<Orcamento>(Orcamento.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Orcamento resultado() throws DadoInvalidoException {
        try {
            Orcamento resultado = new ArmazemDeRegistros<Orcamento>(Orcamento.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
