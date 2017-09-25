package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoVendaDAO;
import br.com.onesystem.domain.ConfiguracaoVenda;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoVendaService implements Serializable {

    @Inject
    private ConfiguracaoVendaDAO dao;

    public ConfiguracaoVendaService() {
    }

    @Produces
    @RequestScoped
    public ConfiguracaoVenda buscar() {
        return dao.resultado();
    }

}
