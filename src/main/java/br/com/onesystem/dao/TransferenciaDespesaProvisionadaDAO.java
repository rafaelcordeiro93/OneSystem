package br.com.onesystem.dao;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TransferenciaDespesaProvisionada;
import br.com.onesystem.util.BundleUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael
 */
public class TransferenciaDespesaProvisionadaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public TransferenciaDespesaProvisionadaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public TransferenciaDespesaProvisionadaDAO buscarTransferencias() {
        consulta += "select t from TransferenciaDespesaProvisionada t where t.id is not null ";
        return this;
    }

    public TransferenciaDespesaProvisionadaDAO ePorEmissaoEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        consulta += "and t.emissao between :pDataInicial and :pDataFinal ";
        return this;
    }
    
    public TransferenciaDespesaProvisionadaDAO ePorDestino(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pDestino", pessoa);
            consulta += "and t.destino.pessoa = :pDestino ";
        }
        return this;
    }
    
    public TransferenciaDespesaProvisionadaDAO ePorOrigem(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pOrigem", pessoa);
            consulta += "and t.origem.pessoa = :pOrigem ";
        }
        return this;
    }

    public List<TransferenciaDespesaProvisionada> listaDeResultados() {
        List<TransferenciaDespesaProvisionada> resultado = new ArmazemDeRegistros<TransferenciaDespesaProvisionada>(TransferenciaDespesaProvisionada.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
