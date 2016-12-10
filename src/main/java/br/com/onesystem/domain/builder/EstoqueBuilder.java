package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class EstoqueBuilder {

    private Long id;
    private Deposito deposito;
    private Item item;
    private BigDecimal saldo;
    private OperacaoFisica tipo;
    private Date emissao = new Date();

    public EstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public EstoqueBuilder comDeposito(Deposito deposito) {
        this.deposito = deposito;
        return this;
    }

    public EstoqueBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public EstoqueBuilder comSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }
    public EstoqueBuilder comTipo(OperacaoFisica tipo) {
        this.tipo = tipo;
        return this;
    }
    

    public Estoque construir() throws DadoInvalidoException {
        return new Estoque(id, item, saldo, deposito, tipo);
    }

}
