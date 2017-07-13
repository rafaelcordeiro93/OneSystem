package br.com.onesystem.war.service;

import br.com.onesystem.dao.ReceitaProvisionadaDAO;
import br.com.onesystem.domain.ReceitaProvisionada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReceitaProvisionadaService implements Serializable {

    public List<ReceitaProvisionada> buscarReceitaProvisionadasAReceber() {
        return new ReceitaProvisionadaDAO().aReceber().listaDeResultados();
    }

    public List<ReceitaProvisionada> buscarReceitaProvisionadasAReceberComVencimentoEntre(Date dataInicial, Date dataFinal) {
        return new ReceitaProvisionadaDAO().aReceber().ePorVencimento(dataInicial, dataFinal).listaDeResultados();
    }

    public List<ReceitaProvisionada> buscarReceitaProvisionadas() {
        return new ReceitaProvisionadaDAO().listaDeResultados();
    }

}
