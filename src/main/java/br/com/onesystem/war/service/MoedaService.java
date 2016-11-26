package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Moeda;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "moedaService")
@ApplicationScoped
public class MoedaService implements Serializable {

    public List<Moeda> buscarMoedas() {
        return new ArmazemDeRegistros<Moeda>(Moeda.class).listaTodosOsRegistros();
    }

}
