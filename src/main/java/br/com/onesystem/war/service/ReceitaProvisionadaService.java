package br.com.onesystem.war.service;

import br.com.onesystem.dao.ReceitaProvisionadaDAO;
import br.com.onesystem.domain.ReceitaProvisionada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class ReceitaProvisionadaService implements Serializable {

    @Inject
    private ReceitaProvisionadaDAO dao;
    
    public List<ReceitaProvisionada> buscarReceitaProvisionadasAReceber() {
        return dao.aReceber().listaDeResultados();
    }

    public List<ReceitaProvisionada> buscarReceitaProvisionadasAReceberComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return dao.aReceber().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<ReceitaProvisionada> buscarReceitaProvisionadas() {
        return dao.listaDeResultados();
    }

}
