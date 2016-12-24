package br.com.onesystem.war.service;


import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Filial;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "filialService")
@ApplicationScoped
public class FilialService implements Serializable {

    public List<Filial> buscarFilials() {
        return new ArmazemDeRegistros<Filial>(Filial.class).listaTodosOsRegistros();
    }
}
