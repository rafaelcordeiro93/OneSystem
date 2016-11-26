package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Receita;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "receitaService")
@ApplicationScoped
public class ReceitaService implements Serializable {
    
    public List<Receita> buscarReceitas(){
        return new ArmazemDeRegistros<Receita>(Receita.class).listaTodosOsRegistros();
    }
    
}
