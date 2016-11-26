package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class MoedaBuilder {

    private Long ID;
    private String nome;
    private String sigla;

    public MoedaBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public MoedaBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public MoedaBuilder comSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public Moeda construir() throws DadoInvalidoException {
        return new Moeda(ID, nome, sigla);
    }

}
