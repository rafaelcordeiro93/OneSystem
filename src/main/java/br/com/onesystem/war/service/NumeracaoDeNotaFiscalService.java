package br.com.onesystem.war.service;

import br.com.onesystem.dao.NumeracaoDeNotaFiscalDAO;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class NumeracaoDeNotaFiscalService implements Serializable {

    @Inject
    private NumeracaoDeNotaFiscalDAO dao;
    
    public List<NumeracaoDeNotaFiscal> buscarNumeracaoDeNotasFiscais() {
        return dao.listaDeResultados();
    }

}
