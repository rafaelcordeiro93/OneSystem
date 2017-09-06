package br.com.onesystem.war.service;

import br.com.onesystem.dao.CartaoDAO;
import br.com.onesystem.domain.Cartao;
import java.io.Serializable;
import java.util.List;

public class CartaoService implements Serializable {

    public List<Cartao> buscarCartaos() {
       return new CartaoDAO().listaDeResultados();
    }
}
