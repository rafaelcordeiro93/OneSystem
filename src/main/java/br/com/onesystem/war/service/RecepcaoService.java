package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.RecepcaoDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "recepcaoService")
@ApplicationScoped
public class RecepcaoService implements Serializable {
    
    public List<Recepcao> buscarRecepcoes(){
        return new ArmazemDeRegistros<Recepcao>(Recepcao.class).listaTodosOsRegistros();
    }

    public String buscarUltimaRecepcaoDa(Pessoa pessoa) {
        return new RecepcaoDAO().buscarDataDaUltimaRecepcaoDaPessoa(pessoa);
    }
    
}
