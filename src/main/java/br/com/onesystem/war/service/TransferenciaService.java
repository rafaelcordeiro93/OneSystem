package br.com.onesystem.war.service;

import br.com.onesystem.dao.TransferenciaDAO;
import br.com.onesystem.domain.Transferencia;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class TransferenciaService implements Serializable {

    @Inject
    private TransferenciaDAO dao;

    public List<Transferencia> buscarTransferencias() {
        return dao.listaDeResultados();
    }

    public List<Transferencia> buscarTransferenciasTipoLancamento() {
        return dao.porTipoLancamento().listaDeResultados();
    }

}
