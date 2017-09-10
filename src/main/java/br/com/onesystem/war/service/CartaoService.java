package br.com.onesystem.war.service;

import br.com.onesystem.dao.CartaoDAO;
import br.com.onesystem.domain.Cartao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CartaoService implements Serializable {

    @Inject
    private CartaoDAO dao;
    
    public List<Cartao> buscarCartaos() {
       return dao.listaDeResultados();
    }
}
