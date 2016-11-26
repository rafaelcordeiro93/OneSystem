package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class ContaBuilder {

    private Long ID;
    private String nome;
    private Banco banco;
    private Moeda moeda;

    public ContaBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public ContaBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public ContaBuilder comBanco(Banco banco) {
        this.banco = banco;
        return this;
    }

    public ContaBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public Conta construir() throws DadoInvalidoException {
        return new Conta(ID, nome, banco, moeda);
    }

}
