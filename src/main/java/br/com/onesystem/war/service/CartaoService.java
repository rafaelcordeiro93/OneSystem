package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cartao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "cartaoService")
@ApplicationScoped
public class CartaoService implements Serializable {

    public List<Cartao> buscarCartaos() {
        return new ArmazemDeRegistros<Cartao>(Cartao.class).listaTodosOsRegistros();
    }
}
