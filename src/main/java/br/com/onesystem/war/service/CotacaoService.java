package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cotacao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "cotacaoService")
@ApplicationScoped
public class CotacaoService implements Serializable {

    public List<Cotacao> buscarCotacoes() {
        return new ArmazemDeRegistros<Cotacao>(Cotacao.class).listaTodosOsRegistros();
    }

}
