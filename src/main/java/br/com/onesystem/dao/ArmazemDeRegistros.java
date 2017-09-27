package br.com.onesystem.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

@Stateful
public class ArmazemDeRegistros<T> implements Serializable {

    private Class<T> classe;

    @PersistenceContext(unitName = "alkatar", type = PersistenceContextType.EXTENDED)
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

    /**
     * 
     * Inicializa a instância lazy.
     * 
     * @param objeto Objeto que contem o campo a ser inicializado
     * @param campo Nome do campo a ser inicializado
     * @param id Id do objeto a ser buscado no banco
     */
    public void initialize(Object objeto, String campo, Long id) {
        //Busca o valor do banco
        System.out.println("1");
        Object objetoInicializado = em.find(classe, id);

        System.out.println("2");
        Class<?> clazz = objeto.getClass();
        try {
            System.out.println("3");
            Field field = clazz.getDeclaredField(campo);
            System.out.println("4");
            field.setAccessible(true);
            System.out.println("5");
            field.set(objeto, objetoInicializado);

            System.out.println("6");
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException("O nome do campo " + campo + " não existe na classe " + clazz);
        } catch (SecurityException ex) {
            throw new RuntimeException("Erro de segurança ao acessar o campo " + campo + " da classe " + clazz);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Erro ao setar o objeto, parametros inválidos");
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Erro ao setar o objeto, não foi possível obter acesso.");
        }
    }

}
