package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Comissao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "comissaoService")
@ApplicationScoped
public class ComissaoService implements Serializable {

    public List<Comissao> buscarComissao() {
        return new ArmazemDeRegistros<Comissao>(Comissao.class).listaTodosOsRegistros();
    }

}
