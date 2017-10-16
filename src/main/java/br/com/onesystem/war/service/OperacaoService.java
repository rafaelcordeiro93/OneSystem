package br.com.onesystem.war.service;

import br.com.onesystem.dao.OperacaoDAO;
import br.com.onesystem.domain.Operacao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class OperacaoService implements Serializable {
    
    @Inject
    private OperacaoDAO dao;
    
    public List<Operacao> buscar(){
        return dao.orderByID().listaDeResultados();
    }
        
}
