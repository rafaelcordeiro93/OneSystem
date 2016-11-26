package br.com.onesystem.war.service;

import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.DespesaProvisionada;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "despesaProvisionadaService")
@ApplicationScoped
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
