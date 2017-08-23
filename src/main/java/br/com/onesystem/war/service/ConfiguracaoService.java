package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;

public class ConfiguracaoService implements Serializable {

    public Configuracao buscar() throws EDadoInvalidoException {
        return new ConfiguracaoDAO().buscar();
    }

}
