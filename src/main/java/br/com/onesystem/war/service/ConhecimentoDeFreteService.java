package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConhecimentoDeFreteDAO;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ConhecimentoDeFreteService implements Serializable {

    @Inject
    private ConhecimentoDeFreteDAO dao;
    
    public List<ConhecimentoDeFrete> buscarConhecimentoDeFrete() {
         return dao.listaDeResultados();
    }
    
}
