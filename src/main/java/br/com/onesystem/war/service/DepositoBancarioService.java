package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.DepositoBancarioDAO;
import br.com.onesystem.domain.DepositoBancario;
import java.io.Serializable;
import java.util.List;

public class DepositoBancarioService implements Serializable {

  public List<DepositoBancario> buscarDepositoBancarios() {
        return new ArmazemDeRegistros<DepositoBancario>(DepositoBancario.class).listaTodosOsRegistros();
    }
  
   public List<DepositoBancario> buscarDepositoBancariosTipoLancamento() {
        return new DepositoBancarioDAO().porTipoLancamento().listaDeResultados();
    }
   
}
