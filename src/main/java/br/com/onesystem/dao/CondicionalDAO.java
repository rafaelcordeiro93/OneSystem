package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class CondicionalDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CondicionalDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "select c from Condicional c where c.id > 0 ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CondicionalDAO porId(Long id) {
        consulta += " and c.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public CondicionalDAO porEstado(EstadoDeCondicional estado) {
        consulta += " and c.estado = :oEstado ";
        parametros.put("oEstado", estado);
        return this;
    }

    public List<Condicional> listaDeResultados() {
        List<Condicional> resultado = new ArmazemDeRegistros<Condicional>(Condicional.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Condicional resultado() throws DadoInvalidoException {
        try {
            Condicional resultado = new ArmazemDeRegistros<Condicional>(Condicional.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
