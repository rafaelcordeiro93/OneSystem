package br.com.onesystem.war.service;

import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ContaService implements Serializable {

    @Inject
    private ContaDAO dao;
    
    public List<Conta> buscarContas() {
        return dao.listaDeResultados();
    }

    public List<Conta> buscarContasPorMoeda(Moeda moeda) {
        return dao.ePorMoeda(moeda).listaDeResultados();
    }

}
