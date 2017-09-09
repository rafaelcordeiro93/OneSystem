package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoVendaDAO;
import br.com.onesystem.domain.ConfiguracaoVenda;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

public class ConfiguracaoVendaService implements Serializable {

    public ConfiguracaoVendaService() {
    }

    @Produces
    @RequestScoped
    public ConfiguracaoVenda buscar() {
        return new ConfiguracaoVendaDAO().resultado();
    }

}
