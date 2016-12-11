package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class PrecoDeItemBuilder {

    private Long ID;
    private Item item;
    private ListaDePreco listaDePreco;
    private BigDecimal valor;
    private Date dataDeExpiracao;

    public PrecoDeItemBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public PrecoDeItemBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public PrecoDeItemBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public PrecoDeItemBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public PrecoDeItemBuilder comDataDeExpiracao(Date dataDeExpiracao) {
        this.dataDeExpiracao = dataDeExpiracao;
        return this;
    }

    public PrecoDeItem construir() throws DadoInvalidoException {
        return new PrecoDeItem(ID, item, listaDePreco, valor, dataDeExpiracao);
    }

}
