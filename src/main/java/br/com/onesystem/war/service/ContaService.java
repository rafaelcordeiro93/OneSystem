package br.com.onesystem.war.service;

import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import java.io.Serializable;
import java.util.List;

public class ContaService implements Serializable {

    public List<Conta> buscarContas() {
        return new ContaDAO().listaDeResultados();
    }

    public List<Conta> buscarContasPorMoeda(Moeda moeda) {
        return new ContaDAO().ePorMoeda(moeda).listaDeResultados();
    }

}
