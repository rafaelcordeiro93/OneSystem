package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConfiguracaoEstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "configuracaoEstoqueService")
@ApplicationScoped
public class ConfiguracaoEstoqueService implements Serializable {

    public ConfiguracaoEstoque buscar() {
        return new ConfiguracaoEstoqueDAO().buscar();
    }

}
