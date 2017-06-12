package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class GrupoBuilder {

    private Long ID;
    private String nome;

    public GrupoBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public GrupoBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

  
    public Grupo construir() throws DadoInvalidoException {
        return new Grupo(ID, nome);
    }

}
