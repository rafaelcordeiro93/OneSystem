package br.com.onesystem.dao;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class PagamentoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public PagamentoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public PagamentoDAO buscarPagamentos() {
        consulta += "select p from Pagamento p ";
        return this;
    }

    public PagamentoDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and p.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public PagamentoDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            consulta += "and p.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public PagamentoDAO groupByPessoa() {
        consulta += "group by p.pessoa ";
        return this;
    }

    public PagamentoDAO orderByMoeda() {
        consulta += "order by p.moeda asc ";
        return this;
    }

    public List<Pagamento> listaDeResultados() {
        List<Pagamento> resultado = new ArmazemDeRegistros<Pagamento>(Pagamento.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Pagamento resultado() throws DadoInvalidoException {
        try {
            Pagamento resultado = new ArmazemDeRegistros<Pagamento>(Pagamento.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
