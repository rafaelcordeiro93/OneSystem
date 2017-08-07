package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class DepositoBuilder {

    private Long ID;
    private String nome;

    public DepositoBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public DepositoBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

  
    public Deposito construir() throws DadoInvalidoException {
        return new Deposito(ID, nome);
    }

}
