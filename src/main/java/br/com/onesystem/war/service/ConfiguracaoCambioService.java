package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoCambioDAO;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ConfiguracaoCambioService implements Serializable {

    @Inject
    private ConfiguracaoCambioDAO dao;
    
    public ConfiguracaoCambio buscar() throws EDadoInvalidoException {
        return dao.buscar();
    }

    public List<Pessoa> buscarPessoas() {
        return dao.buscarPessoas();
    }

}
