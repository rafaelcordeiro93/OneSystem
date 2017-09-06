package br.com.onesystem.war.service;

import br.com.onesystem.dao.ConhecimentoDeFreteDAO;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import java.io.Serializable;
import java.util.List;

public class ConhecimentoDeFreteService implements Serializable {

    public List<ConhecimentoDeFrete> buscarConhecimentoDeFrete() {
         return new ConhecimentoDeFreteDAO().listaDeResultados();
    }
    
}
