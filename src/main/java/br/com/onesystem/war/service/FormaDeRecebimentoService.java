package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.FormaDeRecebimento;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "formaDeRecebimentoService")
@ApplicationScoped
public class FormaDeRecebimentoService implements Serializable {
    
    public List<FormaDeRecebimento> buscarGruposFinanceiros(){
        return new ArmazemDeRegistros<FormaDeRecebimento>(FormaDeRecebimento.class).listaTodosOsRegistros();
    }
}
