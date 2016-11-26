package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.JPAUtil;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GrupoFinanceiroDAO {

    public List<GrupoFinanceiro> buscarGruposDeReceitas() {
        EntityManager manager = JPAUtil.getEntityManager();

        TypedQuery<GrupoFinanceiro> query = manager.createQuery("select g from GrupoFinanceiro g where g.naturezaFinanceira = :pNatureza",
                GrupoFinanceiro.class);

        query.setParameter("pNatureza", NaturezaFinanceira.RECEITA);

        return query.getResultList();
    }

    public List<GrupoFinanceiro> buscarGruposDeDespesas() {
        EntityManager manager = JPAUtil.getEntityManager();

        TypedQuery<GrupoFinanceiro> query = manager.createQuery("select g from GrupoFinanceiro g where g.naturezaFinanceira = :pNatureza",
                GrupoFinanceiro.class);

        query.setParameter("pNatureza", NaturezaFinanceira.DESPESA);

        return query.getResultList();
    }
    
}
