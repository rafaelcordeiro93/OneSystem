package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Usuario;
import java.io.Serializable;
import java.util.List;

public class CaixaService implements Serializable {

    public List<Caixa> buscarCaixas() {
        return new ArmazemDeRegistros<Caixa>(Caixa.class).listaTodosOsRegistros();
    }

    public Caixa getCaixaAbertoDo(Usuario usuario) {
        List<Caixa> lista = new CaixaDAO().buscarCaixas().porUsuario(usuario).emAberto().listaDeResultados();
        if (lista.size() == 1) {
            return lista.get(0);
        } else {
            return null;
        }
    }

}
