package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoContabilDAO;
import br.com.onesystem.domain.ConfiguracaoContabil;
import java.io.Serializable;

public class ConfiguracaoContabilService implements Serializable {

    public ConfiguracaoContabil buscar() {
        return new ConfiguracaoContabilDAO().buscar();
    }

}
