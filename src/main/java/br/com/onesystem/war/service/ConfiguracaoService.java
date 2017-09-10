package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.domain.Configuracao;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoService implements Serializable {

    @Inject
    private ConfiguracaoDAO dao;
    
    public ConfiguracaoService() {
    }

    @RequestScoped
    @Produces
    public Configuracao buscar() {
        return dao.resultado();
    }

}
