package br.com.onesystem.war.service;

import br.com.onesystem.dao.BoletoDeCartaoDAO;
import br.com.onesystem.domain.BoletoDeCartao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class BoletoDeCartaoService implements Serializable {

    @Inject
    private BoletoDeCartaoDAO dao;
    
    public List<BoletoDeCartao> buscarBoletoDeCartaos() {
       return dao.listaDeResultados();
    }
}
