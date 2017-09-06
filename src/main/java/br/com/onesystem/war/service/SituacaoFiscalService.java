package br.com.onesystem.war.service;

import br.com.onesystem.dao.SituacaoFiscalDAO;
import br.com.onesystem.domain.SituacaoFiscal;
import java.io.Serializable;
import java.util.List;

public class SituacaoFiscalService implements Serializable {
    
    public List<SituacaoFiscal> buscarSituacoesFiscais(){
        return new SituacaoFiscalDAO().listaDeResultados();
    }
    
}
