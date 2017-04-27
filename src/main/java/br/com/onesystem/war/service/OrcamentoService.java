package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Orcamento;
import java.io.Serializable;
import java.util.List;

public class OrcamentoService implements Serializable {

    public List<Orcamento> buscarOrcamentos() {
        return new ArmazemDeRegistros<>(Orcamento.class).listaTodosOsRegistros();
    }

}
