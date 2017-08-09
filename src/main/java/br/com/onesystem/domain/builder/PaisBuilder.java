package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class PaisBuilder {

    private String nome;
    private Long codigoPais;
    private Long id;
    private Long codigoReceita;

    public PaisBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PaisBuilder comCodigoPais(Long codigoPais) {
        this.codigoPais = codigoPais;
        return this;
    }

    public PaisBuilder comCodigoReceita(Long codigoReceita) {
        this.codigoReceita = codigoReceita;
        return this;
    }

    public PaisBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public Pais construir() throws DadoInvalidoException {
        return new Pais(id, nome, codigoPais, codigoReceita);
    }

}
