package br.com.onesystem.war.service;

import br.com.onesystem.dao.EstadoDAO;
import br.com.onesystem.domain.Estado;
import java.io.Serializable;
import java.util.List;

public class EstadoService implements Serializable {

    public List<Estado> buscarEstados() {
         return new EstadoDAO().listaDeResultados();
    }

}
