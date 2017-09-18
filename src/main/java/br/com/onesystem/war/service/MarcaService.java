package br.com.onesystem.war.service;

import br.com.onesystem.dao.MarcaDAO;
import br.com.onesystem.domain.Marca;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class MarcaService implements Serializable {

    @Inject
    private MarcaDAO dao;
    
    public List<Marca> buscarMarcas() {
        return dao.listaDeResultados();
    }

}
