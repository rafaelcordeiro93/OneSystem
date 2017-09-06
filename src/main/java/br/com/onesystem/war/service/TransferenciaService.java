package br.com.onesystem.war.service;

import br.com.onesystem.dao.TransferenciaDAO;
import br.com.onesystem.domain.Transferencia;
import java.io.Serializable;
import java.util.List;

public class TransferenciaService implements Serializable {

  public List<Transferencia> buscarTransferencias() {
        return new TransferenciaDAO().listaDeResultados();
    }
  
   public List<Transferencia> buscarTransferenciasTipoLancamento() {
        return new TransferenciaDAO().porTipoLancamento().listaDeResultados();
    }
   
}
