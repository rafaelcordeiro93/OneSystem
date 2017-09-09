package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoContabilDAO;
import br.com.onesystem.domain.ConfiguracaoContabil;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

public class ConfiguracaoContabilService implements Serializable {

    public ConfiguracaoContabilService() {
    }

    @Produces
    @RequestScoped
    public ConfiguracaoContabil buscar() {
        return new ConfiguracaoContabilDAO().resultado();
    }

}
