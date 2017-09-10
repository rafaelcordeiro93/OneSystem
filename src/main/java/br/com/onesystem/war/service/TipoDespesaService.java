package br.com.onesystem.war.service;

import br.com.onesystem.dao.TipoDespesaDAO;
import br.com.onesystem.domain.TipoDespesa;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class TipoDespesaService implements Serializable {
    
    @Inject
    private TipoDespesaDAO dao;
    
    public List<TipoDespesa> buscarTiposDeDespesa(){
        return dao.listaDeResultados();
    }
    
}
