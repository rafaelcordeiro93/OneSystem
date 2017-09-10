package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoEstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoEstoqueService implements Serializable {

    @Inject
    private ConfiguracaoEstoqueDAO dao;
    
    public ConfiguracaoEstoqueService() {
    }

    @Produces
    @RequestScoped
    public ConfiguracaoEstoque buscar() {
        return dao.resultado();
    }

} 
