package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.domain.Configuracao;
import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoService implements Serializable {

    @Inject
    private ConfiguracaoDAO dao;
    
    public ConfiguracaoService() {
    }

    @Produces
    public Configuracao buscar() {
        return dao.resultado();
    }

}
