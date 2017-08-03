package br.com.onesystem.war.service;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.DespesaProvisionada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DespesaProvisionadaService implements Serializable {

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagar() {
        return new DespesaProvisionadaDAO().wAPagar().listaDeResultados();
    }

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new DespesaProvisionadaDAO().wAPagar().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<DespesaProvisionada> buscarDespesaProvisionadas() {
        return new DespesaProvisionadaDAO().listaDeResultados();
    } 

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarDivisaoLucro() {
        return new DespesaProvisionadaDAO().wAPagar().eComDivisaoDeLucro().listaDeResultados();
    }

}
