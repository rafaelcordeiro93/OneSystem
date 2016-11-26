package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class MarcaBuilder {

    private Long ID;
    private String nome;

    public MarcaBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public MarcaBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

  
    public Marca construir() throws DadoInvalidoException {
        return new Marca(ID, nome);
    }

}
