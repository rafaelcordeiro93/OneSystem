package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import java.io.Serializable;
import java.util.List;

public class GrupoFinanceiroService implements Serializable {

    public List<GrupoFinanceiro> buscarGruposFinanceiros() {
        return new GrupoFinanceiroDAO().listaDeResultados();
    }

    public List<GrupoFinanceiro> buscarGruposFInanceirosDoTipoReceitas() {
        return new GrupoFinanceiroDAO().porReceitas().listaDeResultados();
    }

    public List<GrupoFinanceiro> buscarGruposFInanceirosDoTipoDespesas() {
        return new GrupoFinanceiroDAO().porDespesas().listaDeResultados();
    }

}
