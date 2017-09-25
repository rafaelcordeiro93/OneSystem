package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoFinanceiroDAO;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoFinanceiroService implements Serializable {

    @Inject
    private ConfiguracaoFinanceiroDAO dao;
    
    @Produces
    public ConfiguracaoFinanceiro buscar() {
        return dao.resultado();
    }

}
