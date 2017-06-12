package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class CidadeBuilder {

    private Long ID;
    private String nome;
    private String uf;
    private String pais;

    public CidadeBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public CidadeBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public CidadeBuilder comUF(String uf) {
        this.uf = uf;
        return this;
    }

    public CidadeBuilder comPais(String pais) {
        this.pais = pais;
        return this;
    }

    public Cidade construir() throws DadoInvalidoException {
        return new Cidade(ID, nome, uf, pais);
    }

}
