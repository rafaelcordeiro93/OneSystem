package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.NotaEmitida;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "notaEmitidaService")
@ApplicationScoped
public class NotaEmitidaService implements Serializable {

    public List<NotaEmitida> buscarNotasEmitidas() {
        return new ArmazemDeRegistros<>(NotaEmitida.class).listaTodosOsRegistros();
    }

}
