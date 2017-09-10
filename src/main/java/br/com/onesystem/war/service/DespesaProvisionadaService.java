package br.com.onesystem.war.service;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.DespesaProvisionada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class DespesaProvisionadaService implements Serializable {

    @Inject
    private DespesaProvisionadaDAO dao;
    
    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagar() {
        return dao.wAPagar().listaDeResultados();
    }

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return dao.wAPagar().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<DespesaProvisionada> buscarDespesaProvisionadas() {
        return dao.listaDeResultados();
    } 

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarDivisaoLucro() {
        return dao.wAPagar().eComDivisaoDeLucro().listaDeResultados();
    }

}
