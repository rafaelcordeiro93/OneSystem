package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.FaturaLegada;
import java.io.Serializable;
import java.util.List;

public class FaturaLegadaService implements Serializable {

    public List<FaturaLegada> buscarFaturasLegadas() {
        return new ArmazemDeRegistros<>(FaturaLegada.class).listaTodosOsRegistros();
    }

}
