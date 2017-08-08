package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.domain.Pais;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class EstadoBuilder {

    private Long id;
    private String nome;
    private String sigla;
    private Pais pais;

    public EstadoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public EstadoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public EstadoBuilder comSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public EstadoBuilder comPais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public Estado construir() throws DadoInvalidoException {
        return new Estado(id, nome, sigla, pais);
    }

}
