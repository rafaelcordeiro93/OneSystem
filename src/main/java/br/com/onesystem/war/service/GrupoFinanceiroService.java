package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import java.io.Serializable;
import java.util.List;

public class GrupoFinanceiroService implements Serializable {
    
    public List<GrupoFinanceiro> buscarGruposFinanceiros(){
        return new ArmazemDeRegistros<GrupoFinanceiro>(GrupoFinanceiro.class).listaTodosOsRegistros();
    }
    
    public List<GrupoFinanceiro> buscarGruposFInanceirosDoTipoReceitas(){
        return new GrupoFinanceiroDAO().buscarGruposDeReceitas();
    }
    
    public List<GrupoFinanceiro> buscarGruposFInanceirosDoTipoDespesas(){
        return new GrupoFinanceiroDAO().buscarGruposDeDespesas();
    }
    
}
