package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class GrupoFinanceiroService implements Serializable {

    @Inject
    private GrupoFinanceiroDAO dao;
    
    public List<GrupoFinanceiro> buscarGruposFinanceiros() {
        return dao.listaDeResultados();
    }

    public List<GrupoFinanceiro> buscarGruposFInanceirosDoTipoReceitas() {
        return dao.porReceitas().listaDeResultados();
    }

    public List<GrupoFinanceiro> buscarGruposFInanceirosDoTipoDespesas() {
        return dao.porDespesas().listaDeResultados();
    }

}
