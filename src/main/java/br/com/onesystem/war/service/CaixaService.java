package br.com.onesystem.war.service;

import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CaixaService implements Serializable {

    @Inject
    private CaixaDAO dao;
    
    public List<Caixa> buscarCaixas() {
        return dao.listaDeResultados();
    }

    public Caixa getCaixaAbertoDo(Usuario usuario) {
        List<Caixa> lista = dao.porUsuario(usuario).emAberto().listaDeResultados();
        if (lista.size() == 1) {
            return lista.get(0);
        } else {
            return null;
        }
    }

}
