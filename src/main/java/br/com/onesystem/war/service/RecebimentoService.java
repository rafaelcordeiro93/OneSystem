package br.com.onesystem.war.service;

import br.com.onesystem.dao.RecebimentoDAO;
import br.com.onesystem.domain.Recebimento;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class RecebimentoService implements Serializable {
    
    @Inject
    private RecebimentoDAO dao;
    
    public List<Recebimento> buscarRecebimentos() {
        return dao.listaDeResultados();
    }
    
}
