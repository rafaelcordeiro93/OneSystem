package br.com.onesystem.war.service;

import br.com.onesystem.dao.RecebimentoDAO;
import br.com.onesystem.domain.Recebimento;
import java.io.Serializable;
import java.util.List;

public class RecebimentoService implements Serializable {
    
    public List<Recebimento> buscarRecebimentos() {
        return new RecebimentoDAO().listaDeResultados();
    }
    
}
