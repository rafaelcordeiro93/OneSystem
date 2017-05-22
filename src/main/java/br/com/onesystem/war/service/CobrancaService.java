package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cobranca;
import java.io.Serializable;
import java.util.List;

public class CobrancaService implements Serializable {

    public List<Cobranca> buscarCobrancas() {
        return new ArmazemDeRegistros<>(Cobranca.class).listaTodosOsRegistros();
    }

}
