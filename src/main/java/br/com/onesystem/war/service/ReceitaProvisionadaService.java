package br.com.onesystem.war.service;

import br.com.onesystem.dao.ReceitaProvisionadaDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaProvisionada;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "receitaProvisionadaService")
@ApplicationScoped
public class ReceitaProvisionadaService implements Serializable {

    public List<ReceitaProvisionada> buscarReceitaProvisionadasAReceber() {
        return new ReceitaProvisionadaDAO().buscarReceitasProvisionadas().wAReceber().gerarDados();
    }
    
    public List<ReceitaProvisionada> buscarReceitaProvisionadas() {
        return new ReceitaProvisionadaDAO().buscarReceitasProvisionadas().gerarDados();
    }
    
}
