package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class GrupoFinanceiroDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public GrupoFinanceiroDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public GrupoFinanceiroDAO buscarGrupoFinanceiros() {
        consulta += "select g from GrupoFinanceiro g where g.id > 0 ";
        return this;
    }

    public GrupoFinanceiroDAO porId(Long id) {
        consulta += " and g.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoFinanceiroDAO porNome(GrupoFinanceiro grupoFinanceiro) {
        consulta += " and g.nome = :pNome ";
        parametros.put("pNome", grupoFinanceiro.getNome());
        return this;
    }

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

    public List<GrupoFinanceiro> listaDeResultados() {
        List<GrupoFinanceiro> resultado = new ArmazemDeRegistros<GrupoFinanceiro>(GrupoFinanceiro.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public GrupoFinanceiro resultado() throws DadoInvalidoException {
        try {
            GrupoFinanceiro resultado = new ArmazemDeRegistros<GrupoFinanceiro>(GrupoFinanceiro.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
