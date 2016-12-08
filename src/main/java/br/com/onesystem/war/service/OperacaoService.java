package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Operacao;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "operacaoService")
@ApplicationScoped
public class OperacaoService implements Serializable {
    
    public List<Operacao> buscarOperacao(){
        return new ArmazemDeRegistros<Operacao>(Operacao.class).listaTodosOsRegistros();
    }
    
}
