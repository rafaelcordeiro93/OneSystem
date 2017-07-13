package br.com.onesystem.dao;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class ReceitaProvisionadaDAO extends GenericDAO<ReceitaProvisionada> {

    public ReceitaProvisionadaDAO() {
        limpar();
    }

    protected void limpar() {
        query = "select rp from ReceitaProvisionada rp ";
        join = " ";
        where = " where rp.id != 0 ";
        order = " ";
        group = " ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ReceitaProvisionadaDAO aReceber() {
        where += "and 0 = (select count(*) from Baixa b where b.cobrancaFixa = rp.id and b.estado = :pBNaoCancelada) ";
        parametros.put("pBNaoCancelada", EstadoDeBaixa.CANCELADO);
        return this;
    }

    public ReceitaProvisionadaDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and rp.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public ReceitaProvisionadaDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and (rp.vencimento between :pDataInicial and :pDataFinal or rp.vencimento is null) ";
        }
        return this;
    }

    public ReceitaProvisionadaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            where += "and rp.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public ReceitaProvisionadaDAO groupByPessoa() {
        group += "group by rp.pessoa ";
        return this;
    }

    public ReceitaProvisionadaDAO orderByMoeda() {
        order += "order by rp.moeda asc ";
        return this;
    }

    public List<ReceitaProvisionada> listaDeResultados() {
        List<ReceitaProvisionada> resultado = new ArmazemDeRegistros<ReceitaProvisionada>(ReceitaProvisionada.class)
                .listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return resultado;
    }

    @Override
    public ReceitaProvisionada resultado() throws DadoInvalidoException {
        try {
            ReceitaProvisionada resultado = new ArmazemDeRegistros<ReceitaProvisionada>(ReceitaProvisionada.class)
                    .resultadoUnicoDaConsulta(getConsulta(), parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
