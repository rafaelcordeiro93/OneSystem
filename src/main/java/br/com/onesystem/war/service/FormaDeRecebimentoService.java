package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.FormaDeRecebimentoDAO;
import br.com.onesystem.domain.FormaDeRecebimento;
import java.io.Serializable;
import java.util.List;

public class FormaDeRecebimentoService implements Serializable {

    public List<FormaDeRecebimento> buscarFormasDeRecebimento() {
        return new ArmazemDeRegistros<FormaDeRecebimento>(FormaDeRecebimento.class).listaTodosOsRegistros();
    }

    public List<FormaDeRecebimento> buscarFormasDeRecebimentoAtivas() {
        return new FormaDeRecebimentoDAO().buscarFormasDeRecebimento().ativas().listaDeResultados();
    }
}
