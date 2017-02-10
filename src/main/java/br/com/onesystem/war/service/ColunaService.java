package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Coluna;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "colunaService")
@ApplicationScoped
public class ColunaService implements Serializable {

    public List<Coluna> buscarColuna() {
        return new ArmazemDeRegistros<Coluna>(Coluna.class).listaTodosOsRegistros();
    }

}
