package br.com.onesystem.war.service;

import br.com.onesystem.dao.DepositoBancarioDAO;
import br.com.onesystem.domain.DepositoBancario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class DepositoBancarioService implements Serializable {

    @Inject
    private DepositoBancarioDAO dao;
    
  public List<DepositoBancario> buscarDepositoBancarios() {
        return dao.listaDeResultados();
    }
  
   public List<DepositoBancario> buscarDepositoBancariosTipoLancamento() {
        return dao.porTipoLancamento().listaDeResultados();
    }
   
}
