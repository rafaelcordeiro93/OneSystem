package br.com.onesystem.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class ArmazemDeRegistros<T> implements Serializable {

    private Class<T> classe;

    @Inject
    private EntityManager em;

    public ArmazemDeRegistros() {
    }

    public ArmazemDeRegistros daClasse(Class<T> classe) {
        this.classe = classe;
        return this;
    }

    public ArmazemDeRegistros daClasse(Class<T> classe, EntityManager em) {
        this.em = em;
        this.classe = classe;
        return this;
    }

    public List<T> listaTodosOsRegistros() {
        List<T> lista = new ArrayList<>();
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));
        lista = em.createQuery(query).getResultList();

        return lista;
    }

    public List<T> listaRegistrosDaConsulta(String consulta, Map<String, Object> parametros) {
        TypedQuery<T> query = em.createQuery(consulta, classe);
        adicionarParametrosNaConsulta(query, parametros);
        return query.getResultList();
    }

    public T resultadoUnicoDaConsulta(String consulta, Map<String, Object> parametros) {
        try {
            TypedQuery<T> query = em.createQuery(consulta, classe);
            adicionarParametrosNaConsulta(query, parametros);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            nre.getMessage();
        }
        return null;
    }

    public BigDecimal resultadoOperacaoMatematica(String consulta, Map<String, Object> parametros) throws NoResultException {
        TypedQuery<BigDecimal> query = em.createQuery(consulta, BigDecimal.class);
        adicionarParametrosNaConsulta(query, parametros);
        return query.getSingleResult();
    }

    private void adicionarParametrosNaConsulta(Query query, Map<String, Object> parametros) {
        for (Map.Entry<String, Object> map : parametros.entrySet()) {
            query.setParameter(map.getKey(), map.getValue());
        }
    }

    public T find(Long id) {
        return em.find(classe, id);
    }

}
