package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.GrupoFinanceiroDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "grupoFinanceiroService")
@ApplicationScoped
public class GrupoFinanceiroService implements Serializable {
    
    public List<GrupoFinanceiro> buscarGruposFinanceiros(){
        return new ArmazemDeRegistros<GrupoFinanceiro>(GrupoFinanceiro.class).listaTodosOsRegistros();
    }
    
    public List<GrupoFinanceiro> buscarGruposDeReceitas(){
        return new GrupoFinanceiroDAO().buscarGruposDeReceitas();
    }
    
    public List<GrupoFinanceiro> buscarGruposDeDespesas(){
        return new GrupoFinanceiroDAO().buscarGruposDeDespesas();
    }
    
}
