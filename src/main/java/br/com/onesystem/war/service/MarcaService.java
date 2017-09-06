package br.com.onesystem.war.service;

import br.com.onesystem.dao.MarcaDAO;
import br.com.onesystem.domain.Marca;
import java.io.Serializable;
import java.util.List;

public class MarcaService implements Serializable {

    public List<Marca> buscarMarcas() {
        return new MarcaDAO().listaDeResultados();
    }

}
