package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoCambioDAO;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "configuracaoCambioService")
@ApplicationScoped
public class ConfiguracaoCambioService implements Serializable {

    public ConfiguracaoCambio buscar() {
        return new ConfiguracaoCambioDAO().buscar();
    }

    public List<Pessoa> buscarPessoas() {
        return new ConfiguracaoCambioDAO().buscarPessoas();
    }

}
