package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Cordeiro
 */
public class MotivoBuilder {

    private Long id;
    private String nome;

    public MotivoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public MotivoBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public Motivo construir() throws DadoInvalidoException {
        return new Motivo(id, nome);
    }

}
