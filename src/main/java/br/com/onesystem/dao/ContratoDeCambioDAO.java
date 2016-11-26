package br.com.onesystem.dao;

import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.JPAUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ContratoDeCambioDAO {

    public List<ContratoDeCambio> buscarContratosDeHoje() {
        EntityManager manager = JPAUtil.getEntityManager();

        TypedQuery<ContratoDeCambio> query = manager.createQuery("select c from ContratoDeCambio c where c.emissao "
                + " between :pDataInicial and :pDataFinal",
                ContratoDeCambio.class);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, +1);

        query.setParameter("pDataInicial", new Date());
        query.setParameter("pDataFinal", c.getTime());

        return query.getResultList();
    }
    
    public List<ContratoDeCambio> buscarContratosFechadosParaCambio() {
        EntityManager manager = JPAUtil.getEntityManager();

        TypedQuery<ContratoDeCambio> query = manager.createQuery("select c from ContratoDeCambio c LEFT JOIN c.cambio ca"
                + " where c.status = :pStatus and ca.contrato is null",
                ContratoDeCambio.class);

        query.setParameter("pStatus", true);

        return query.getResultList();
    }
    
    public List<ContratoDeCambio> buscarContratoDeCambioPor(Date dataInicial, Date dataFinal, Pessoa pessoa,
            Boolean status) throws DadoInvalidoException {
        EntityManager manager = JPAUtil.getEntityManager();

        String consulta = "select c from ContratoDeCambio c where "
                + "c.emissao between :pDataInicial and :pDataFinal "
                + (pessoa != null ? "and c.pessoa = :pPessoa " : "")
                + (status != null ? "and c.status = :pStatus " : ""
                + "order by c.origem, c.destino, c.emissao ");

        TypedQuery<ContratoDeCambio> query = manager.createQuery(consulta, ContratoDeCambio.class);

        query.setParameter("pDataInicial", dataInicial);
        query.setParameter("pDataFinal", dataFinal);
        if (pessoa != null) {
            query.setParameter("pPessoa", pessoa);
        }
        if (status != null) {
            query.setParameter("pStatus", status);
        }
        List<ContratoDeCambio> lista = query.getResultList();
        return lista;
    }

}
