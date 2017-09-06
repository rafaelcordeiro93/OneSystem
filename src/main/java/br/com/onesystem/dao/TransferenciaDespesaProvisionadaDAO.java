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
public class TransferenciaDespesaProvisionadaDAO extends GenericDAO<TransferenciaDespesaProvisionada> {

    public TransferenciaDespesaProvisionadaDAO() {
        super(TransferenciaDespesaProvisionada.class);
    }

    public TransferenciaDespesaProvisionadaDAO ePorEmissaoEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        where += "and transferenciaDespesaProvisionada.emissao between :pDataInicial and :pDataFinal ";
        return this;
    }

    public TransferenciaDespesaProvisionadaDAO ePorDestino(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pDestino", pessoa);
            where += "and transferenciaDespesaProvisionada.destino.pessoa = :pDestino ";
        }
        return this;
    }

    public TransferenciaDespesaProvisionadaDAO ePorOrigem(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pOrigem", pessoa);
            where += "and transferenciaDespesaProvisionada.origem.pessoa = :pOrigem ";
        }
        return this;
    }

}
