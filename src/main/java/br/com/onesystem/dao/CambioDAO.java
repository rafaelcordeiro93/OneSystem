package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.util.Date;
import java.util.List;

public class CambioDAO extends GenericDAO<Cambio> {

    public CambioDAO() {
        super(Cambio.class);
        limpar();
    }

    public List<Cambio> buscarCambioPor(Date dataInicial, Date dataFinal, Pessoa pessoa) throws DadoInvalidoException {
//        EntityManager manager = JPAUtil.getEntityManager();
//
//        String consulta = "select c from Cambio c where "
//                + "c.emissao between :pDataInicial and :pDataFinal "
//                + (pessoa != null ? "and c.contrato.pessoa = :pPessoa" : ""
//                + "order by c.contrato.origem.id, c.emissao");
//
//        TypedQuery<Cambio> query = manager.createQuery(consulta, Cambio.class);
//
//        query.setParameter("pDataInicial", dataInicial);
//        query.setParameter("pDataFinal", dataFinal);
//        if (pessoa != null) {
//            query.setParameter("pPessoa", pessoa);
//        }
//        List<Cambio> lista = query.getResultList();
//        return lista;
        return null;

    }

}
