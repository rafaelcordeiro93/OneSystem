package br.com.onesystem.war.service;

import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Usuario;
import java.io.Serializable;
import java.util.List;

public class CaixaService implements Serializable {

    public List<Caixa> buscarCaixas() {
        return new CaixaDAO().listaDeResultados();
    }

    public Caixa getCaixaAbertoDo(Usuario usuario) {
        List<Caixa> lista = new CaixaDAO().porUsuario(usuario).emAberto().listaDeResultados();
        if (lista.size() == 1) {
            return lista.get(0);
        } else {
            return null;
        }
    }

}
