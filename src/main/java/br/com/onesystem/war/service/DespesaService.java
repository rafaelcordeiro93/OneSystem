package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Despesa;
import java.io.Serializable;
import java.util.List;

public class DespesaService implements Serializable {
    
    public List<Despesa> buscarDespesas(){
        return new ArmazemDeRegistros<Despesa>(Despesa.class).listaTodosOsRegistros();
    }
    
}
