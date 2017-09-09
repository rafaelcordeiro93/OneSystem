package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoFinanceiroDAO;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import java.io.Serializable;

public class ConfiguracaoFinanceiroService implements Serializable {

    public ConfiguracaoFinanceiro buscar() {
        return new ConfiguracaoFinanceiroDAO().resultado();
    }

}
