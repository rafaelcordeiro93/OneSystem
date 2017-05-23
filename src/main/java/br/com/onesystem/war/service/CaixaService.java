package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Caixa;
import java.io.Serializable;
import java.util.List;

public class CaixaService implements Serializable {
    
    public List<Caixa> buscarCaixas(){
        return new ArmazemDeRegistros<Caixa>(Caixa.class).listaTodosOsRegistros();
    }
    
}
