package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoEstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

public class ConfiguracaoEstoqueService implements Serializable {

    public ConfiguracaoEstoqueService() {
    }

    @Produces
    @RequestScoped
    public ConfiguracaoEstoque buscar() {
        return new ConfiguracaoEstoqueDAO().resultado();
    }

} 
