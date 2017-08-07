package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.GrupoFiscal;
import java.io.Serializable;
import java.util.List;

public class GrupoFiscalService implements Serializable {
    
    public List<GrupoFiscal> buscarGrupoFiscais(){
        return new ArmazemDeRegistros<GrupoFiscal>(GrupoFiscal.class).listaTodosOsRegistros();
    }
    
}
