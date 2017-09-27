package br.com.onesystem.war.service;

import br.com.onesystem.dao.LoteNotaFiscalDAO;
import br.com.onesystem.domain.LoteNotaFiscal;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class LoteNotaFiscalService implements Serializable {

    @Inject
    private LoteNotaFiscalDAO dao;
    
    public List<LoteNotaFiscal> buscarLoteNotaFiscais() {
        return dao.listaDeResultados();
    }

}
