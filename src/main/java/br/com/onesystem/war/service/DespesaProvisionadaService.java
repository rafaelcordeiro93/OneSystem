package br.com.onesystem.war.service;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.DespesaProvisionada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DespesaProvisionadaService implements Serializable {

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagar() {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().wAPagar().gerarDados();
    }

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().wAPagar().ePorVencimento(dataInicial, dataFinal).gerarDados();
    }

    public List<DespesaProvisionada> buscarDespesaProvisionadas() {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().gerarDados();
    } 

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarDivisaoLucro() {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().wAPagar().eComDivisaoDeLucro().gerarDados();
    }

}
