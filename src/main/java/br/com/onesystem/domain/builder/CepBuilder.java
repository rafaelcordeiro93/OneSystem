package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class CepBuilder {

    private Long ID;
    private String cep;
    private Cidade cidade;

    public CepBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public CepBuilder comCep(String nome) {
        this.cep = nome;
        return this;
    }

    public CepBuilder comCidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }

    public Cep construir() throws DadoInvalidoException {
        return new Cep(ID, cep, cidade);
    }

}
