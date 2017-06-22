package br.com.onesystem.dao;

import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class RecebimentoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public RecebimentoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public RecebimentoDAO buscarReceitasProvisionadas() {
        consulta += "select rp from Recebimento rp ";
        return this;
    }

    public RecebimentoDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and rp.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public RecebimentoDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            consulta += "and rp.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public RecebimentoDAO groupByPessoa() {
        consulta += "group by rp.pessoa ";
        return this;
    }

    public RecebimentoDAO orderByMoeda() {
        consulta += "order by rp.moeda asc ";
        return this;
    }

    public List<Recebimento> listaDeResultados() {
        List<Recebimento> resultado = new ArmazemDeRegistros<Recebimento>(Recebimento.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Recebimento resultado() throws DadoInvalidoException {
        try {
            Recebimento resultado = new ArmazemDeRegistros<Recebimento>(Recebimento.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
