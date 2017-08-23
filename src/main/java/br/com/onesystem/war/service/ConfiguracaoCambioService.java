package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoCambioDAO;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;

public class ConfiguracaoCambioService implements Serializable {

    public ConfiguracaoCambio buscar() throws EDadoInvalidoException {
        return new ConfiguracaoCambioDAO().buscar();
    }

    public List<Pessoa> buscarPessoas() {
        return new ConfiguracaoCambioDAO().buscarPessoas();
    }

}
