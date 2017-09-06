package br.com.onesystem.war.service;

import br.com.onesystem.dao.CambioDAO;
import br.com.onesystem.domain.Cambio;
import java.io.Serializable;
import java.util.List;

public class CambioService implements Serializable {
    
    public List<Cambio> buscarCambios(){
        return new CambioDAO().listaDeResultados();
    }
    
}
