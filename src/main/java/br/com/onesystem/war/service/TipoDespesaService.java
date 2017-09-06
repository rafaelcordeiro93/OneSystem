package br.com.onesystem.war.service;

import br.com.onesystem.dao.TipoDespesaDAO;
import br.com.onesystem.domain.TipoDespesa;
import java.io.Serializable;
import java.util.List;

public class TipoDespesaService implements Serializable {
    
    public List<TipoDespesa> buscarTiposDeDespesa(){
        return new TipoDespesaDAO().listaDeResultados();
    }
    
}
