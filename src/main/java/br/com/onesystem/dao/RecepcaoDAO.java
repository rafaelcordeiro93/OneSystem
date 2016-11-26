package br.com.onesystem.dao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class RecepcaoDAO {

    public String buscarDataDaUltimaRecepcaoDaPessoa(Pessoa pessoa) {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Date> query = manager.createQuery("select max(emissao) from Recepcao r where r.pessoa = :pPessoa", Date.class);

        query.setParameter("pPessoa", pessoa);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String resultado = "";
        try {
            resultado = sdf.format(query.getSingleResult());
            return resultado;
        } catch (NullPointerException npe) {
            return "Não existe.";
        }
    }

    public List<Recepcao> buscarRecepcoesPor(Date dataInicial, Date dataFinal, Pessoa pessoa,
            Conta conta) throws DadoInvalidoException {
        EntityManager manager = JPAUtil.getEntityManager();

        String consulta = "select r from Recepcao r where "
                + "r.emissao between :pDataInicial and :pDataFinal "
                + (pessoa != null ? "and r.pessoa = :pPessoa " : "")
                + (conta != null ? "and r.conta = :pConta " : ""
                + "order by r.conta.moeda ");

        TypedQuery<Recepcao> query = manager.createQuery(consulta, Recepcao.class);

        query.setParameter("pDataInicial", dataInicial);
        query.setParameter("pDataFinal", dataFinal);
        if (pessoa != null) {
            query.setParameter("pPessoa", pessoa);
        }
        if (conta != null) {
            query.setParameter("pConta", conta);
        }
        List<Recepcao> lista = query.getResultList();
        return lista;
    }

}
