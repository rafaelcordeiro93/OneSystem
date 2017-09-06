package br.com.onesystem.war.service;

import br.com.onesystem.dao.OperacaoDAO;
import br.com.onesystem.domain.Operacao;
import java.io.Serializable;
import java.util.List;

public class OperacaoService implements Serializable {
    
    public List<Operacao> buscar(){
        return new OperacaoDAO().listaDeResultados();
    }
        
}
