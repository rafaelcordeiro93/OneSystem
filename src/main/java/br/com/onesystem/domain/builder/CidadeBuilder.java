package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class CidadeBuilder {

    private Long ID;
    private String nome;
    private Estado estado;

    public CidadeBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public CidadeBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public CidadeBuilder comEstado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public Cidade construir() throws DadoInvalidoException {
        return new Cidade(ID, nome, estado);
    }

}
