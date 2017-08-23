package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoVendaDAO;
import br.com.onesystem.domain.ConfiguracaoVenda;
import java.io.Serializable;

public class ConfiguracaoVendaService implements Serializable {

    public ConfiguracaoVenda buscar() {
        return new ConfiguracaoVendaDAO().buscar();
    }

}
