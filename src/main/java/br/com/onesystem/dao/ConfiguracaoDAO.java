package br.com.onesystem.dao;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfiguracaoDAO {

    public Configuracao buscar() throws EDadoInvalidoException {
        try {
            EntityManager manager = JPAUtil.getEntityManager();
            TypedQuery<Configuracao> query = manager.createNamedQuery("Configuracao.busca", Configuracao.class);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
        }
    }
}
