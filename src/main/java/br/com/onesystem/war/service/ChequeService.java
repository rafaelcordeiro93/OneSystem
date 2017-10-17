package br.com.onesystem.war.service;

import br.com.onesystem.dao.ChequeDAO;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ChequeService implements Serializable {

    @Inject
    private ChequeDAO dao;

    public List<Cheque> buscarCheques() {
        return dao.listaDeResultados();
    }

    public List<Cheque> buscaChequesPor(TipoLancamento tipoLancamento, EstadoDeCheque estadoDeCheque) {
        return dao.porEstado(estadoDeCheque).porTipoLancamento(tipoLancamento).listaDeResultados();
    }

}
