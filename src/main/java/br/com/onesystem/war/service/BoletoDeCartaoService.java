package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.BoletoDeCartao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "boletoDeCartaoService")
@ApplicationScoped
public class BoletoDeCartaoService implements Serializable {

    public List<BoletoDeCartao> buscarBoletoDeCartaos() {
        return new ArmazemDeRegistros<BoletoDeCartao>(BoletoDeCartao.class).listaTodosOsRegistros();
    }
}
