package br.com.onesystem.dao;

import br.com.onesystem.domain.Comanda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeComanda;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class ComandaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ComandaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "select c from Comanda c where c.id > 0 ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ComandaDAO porId(Long id) {
        consulta += " and c.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public ComandaDAO porEstado(EstadoDeComanda estado) {
        consulta += " and c.estado = :oEstado ";
        parametros.put("oEstado", estado);
        return this;
    }

    public List<Comanda> listaDeResultados() {
        List<Comanda> resultado = new ArmazemDeRegistros<Comanda>(Comanda.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Comanda resultado() throws DadoInvalidoException {
        try {
            Comanda resultado = new ArmazemDeRegistros<Comanda>(Comanda.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
