package br.com.onesystem.war.service;

import br.com.onesystem.dao.ColunaDAO;
import br.com.onesystem.domain.Coluna;
import java.io.Serializable;
import java.util.List;

public class ColunaService implements Serializable {

    public List<Coluna> buscarColuna() {
        return new ColunaDAO().listaDeResultados();
    }

}
