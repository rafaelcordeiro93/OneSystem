package br.com.onesystem.dao;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class FormaDeRecebimentoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public FormaDeRecebimentoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public FormaDeRecebimentoDAO buscarFormasDeRecebimento() {
        consulta += "select f from FormaDeRecebimento f where f.id > 0 ";
        return this;
    }

    public FormaDeRecebimentoDAO porId(Long id) {
        consulta += " and f.id = :fId ";
        parametros.put("fId", id);
        return this;
    }

    public FormaDeRecebimentoDAO ativas() {
        consulta += " and f.ativo = :pAtiva ";
        parametros.put("pAtiva", true);
        return this;
    }

    public List<FormaDeRecebimento> listaDeResultados() {
        List<FormaDeRecebimento> resultado = new ArmazemDeRegistros<FormaDeRecebimento>(FormaDeRecebimento.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public FormaDeRecebimento resultado() throws DadoInvalidoException {
        try {
            FormaDeRecebimento resultado = new ArmazemDeRegistros<FormaDeRecebimento>(FormaDeRecebimento.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
