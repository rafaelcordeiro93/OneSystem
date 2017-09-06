package br.com.onesystem.war.service;

import br.com.onesystem.dao.BoletoDeCartaoDAO;
import br.com.onesystem.domain.BoletoDeCartao;
import java.io.Serializable;
import java.util.List;

public class BoletoDeCartaoService implements Serializable {

    public List<BoletoDeCartao> buscarBoletoDeCartaos() {
       return new BoletoDeCartaoDAO().listaDeResultados();
    }
}
