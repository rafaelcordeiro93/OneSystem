package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "configuracaoService")
@ApplicationScoped
public class ConfiguracaoService implements Serializable {

    public Configuracao buscar() throws EDadoInvalidoException {
        return new ConfiguracaoDAO().buscar();
    }

}
