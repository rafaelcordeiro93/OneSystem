package br.com.onesystem.dao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.util.JPAUtil;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class ArmazemDeRegistros<T> {

    private final Class<T> classe;

    public ArmazemDeRegistros(Class<T> classe) {
        this.classe = classe;
    }

    public List<T> listaTodosOsRegistros() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));
        List<T> lista = entityManager.createQuery(query).getResultList();

        return lista;
    }

    public List<T> listaRegistrosDaConsulta(String consulta, Map<String, Object> parametros) {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<T> query = manager.createQuery(consulta, classe);
        adicionarParametrosNaConsulta(query, parametros);
        return query.getResultList();
    }

    public T resultadoUnicoDaConsulta(String consulta, Map<String, Object> parametros) {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<T> query = manager.createQuery(consulta, classe);
        adicionarParametrosNaConsulta(query, parametros);
        return query.getSingleResult();
    }

    private void adicionarParametrosNaConsulta(TypedQuery<T> query, Map<String, Object> parametros) {
        for (Map.Entry<String, Object> map : parametros.entrySet()) {
            query.setParameter(map.getKey(), map.getValue());
        }
    }

    public T find(T objeto) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        return entityManager.find(classe, objeto);
    }

}
