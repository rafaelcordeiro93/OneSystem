package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.ReceitaProvisionadaDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaProvisionada;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "conhecimentoDeFreteService")
@ApplicationScoped
public class ConhecimentoDeFreteService implements Serializable {

    public List<ConhecimentoDeFrete> buscarConhecimentoDeFreteService() {
        return new ArmazemDeRegistros<ConhecimentoDeFrete>(ConhecimentoDeFrete.class).listaTodosOsRegistros();
    }
    
}
