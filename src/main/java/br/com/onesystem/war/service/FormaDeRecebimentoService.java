package br.com.onesystem.war.service;

import br.com.onesystem.dao.FormaDeRecebimentoDAO;
import br.com.onesystem.domain.FormaDeRecebimento;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class FormaDeRecebimentoService implements Serializable {

    @Inject
    private FormaDeRecebimentoDAO dao;
    
    public List<FormaDeRecebimento> buscarFormasDeRecebimento() {
        return dao.listaDeResultados();
    }

    public List<FormaDeRecebimento> buscarFormasDeRecebimentoAtivas() {
        return dao.ativas().listaDeResultados();
    }
}
