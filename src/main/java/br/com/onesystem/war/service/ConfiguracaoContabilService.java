package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoContabilDAO;
import br.com.onesystem.domain.ConfiguracaoContabil;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoContabilService implements Serializable {

    @Inject
    private ConfiguracaoContabilDAO dao;
    
    public ConfiguracaoContabilService() {
    }

    @Produces
    public ConfiguracaoContabil buscar() {
        return dao.resultado();
    }

}
