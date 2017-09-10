package br.com.onesystem.war.service;

import br.com.onesystem.dao.RecepcaoDAO;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class RecepcaoService implements Serializable {
    
    @Inject
    private RecepcaoDAO dao;
    
    public List<Recepcao> buscarRecepcoes(){
        return dao.listaDeResultados();
    }

    public String buscarUltimaRecepcaoDa(Pessoa pessoa) {
        return dao.buscarDataDaUltimaRecepcaoDaPessoa(pessoa);
    }
    
}
