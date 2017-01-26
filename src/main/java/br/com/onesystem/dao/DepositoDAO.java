package br.com.onesystem.dao;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class DepositoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public DepositoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public DepositoDAO buscarDepositos() {
        consulta += "select d from Deposito d where d.id > 0 ";
        return this;
    }

    public DepositoDAO porId(Long id) {
        consulta += " and d.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

    public DepositoDAO porNome(Deposito deposito) {
        consulta += " and d.nome = :dNome ";
        parametros.put("dNome", deposito.getNome());
        return this;
    }

    public List<Deposito> listaDeResultados() {
        List<Deposito> resultado = new ArmazemDeRegistros<Deposito>(Deposito.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Deposito resultado() throws DadoInvalidoException {
        try {
            Deposito resultado = new ArmazemDeRegistros<Deposito>(Deposito.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
