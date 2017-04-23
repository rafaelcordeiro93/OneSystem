package br.com.onesystem.war.service;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.DespesaProvisionada;
import java.io.Serializable;
import java.util.List;

public class DespesaProvisionadaService implements Serializable {

    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagar() {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().wAPagar().gerarDados();
    }
    
    public List<DespesaProvisionada> buscarDespesaProvisionadas() {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().gerarDados();
    }
    
    public List<DespesaProvisionada> buscarDespesaProvisionadasAPagarDivisaoLucro() {
        return new DespesaProvisionadaDAO().buscarDespesasProvisionadas().wAPagar().eComDivisaoDeLucro().gerarDados();
    }  
    
}
