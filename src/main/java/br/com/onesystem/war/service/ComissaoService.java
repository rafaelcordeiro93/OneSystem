package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Comissao;
import java.io.Serializable;
import java.util.List;

public class ComissaoService implements Serializable {

    public List<Comissao> buscarComissao() {
        return new ArmazemDeRegistros<Comissao>(Comissao.class).listaTodosOsRegistros();
    }

}
