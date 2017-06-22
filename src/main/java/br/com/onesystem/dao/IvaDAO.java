package br.com.onesystem.dao;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class IvaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public IvaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public IvaDAO buscarIVA() {
        consulta += "select d from IVA d where d.id > 0 ";
        return this;
    }

    public IvaDAO porId(Long id) {
        consulta += " and d.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

    public IvaDAO porNome(IVA deposito) {
        consulta += " and d.nome = :dNome ";
        parametros.put("dNome", deposito.getNome());
        return this;
    }

    public List<IVA> listaDeResultados() {
        List<IVA> resultado = new ArmazemDeRegistros<IVA>(IVA.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public IVA resultado() throws DadoInvalidoException {
        try {
            IVA resultado = new ArmazemDeRegistros<IVA>(IVA.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
