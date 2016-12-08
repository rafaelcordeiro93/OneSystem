package br.com.onesystem.dao;

import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfiguracaoCambioDAO {

    public ConfiguracaoCambio buscar() throws EDadoInvalidoException {
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<ConfiguracaoCambio> query = manager.createNamedQuery("ConfiguracaoCambio.busca", ConfiguracaoCambio.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_Cambio_nao_definida"));
        }
    }

    public List<Pessoa> buscarPessoas() {
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<Pessoa> query = manager.createNamedQuery("ConfiguracaoCambio.buscaPessoas", Pessoa.class);
            return query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
